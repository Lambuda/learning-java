package com.lin.ds.itms.thread.flush;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 在高并发系统中，我们经常遇到这样的需求：系统产生大量的请求，但是这些请求实时性要求不高。
 * 我们就可以将这些请求合并，达到一定数量我们统一提交。最大化的利用系统性IO,提升系统的吞吐性能。
 * <p>
 * 所以请求合并框架需要考虑以下两个需求：
 * <p>
 * 1、当请求收集到一定数量时提交数据
 * 2、一段时间后如果请求没有达到指定的数量也进行提交
 * <p>
 * - ScheduledThreadPoolExecutor
 * - 阻塞队列
 * - 线程安全的参数
 * - LockSupport的使用
 *
 * @param <Item>
 */
public class Flusher<Item> {

    private final FlushThread<Item>[] flushThreads;

    private AtomicInteger index;

    //防止多个线程同时执行。增加一个随机数间隔
    private static final Random r = new Random();

    private static final int delta = 50;

    private static ScheduledExecutorService TIMER = new ScheduledThreadPoolExecutor(1);

    private static ExecutorService POOL = Executors.newCachedThreadPool();

    public Flusher(String name, int bufferSiz, int flushInterval, int queueSize, int threads, Processor<Item> processor) {

        this.flushThreads = new FlushThread[threads];


        if (threads > 1) {
            index = new AtomicInteger();
        }

        for (int i = 0; i < threads; i++) {
            final FlushThread<Item> flushThread = new FlushThread<Item>(name + "-" + i, bufferSiz, flushInterval, queueSize, processor);
            flushThreads[i] = flushThread;
            POOL.submit(flushThread);
            //定时调用 timeOut()方法。
            TIMER.scheduleAtFixedRate(flushThread::timeOut, r.nextInt(delta), flushInterval, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 我们的数据结构需要提供一个 add() 的方法给外部，用于提交数据。
     * 当外部add数据以后，需要检查队列里面的数据的个数是否达到我们限额？达到数量提交数据，不达到继续等待。
     * @param item
     * @return
     */
    // 对 index 取模，保证多线程都能被add
    public boolean add(Item item) {
        int len = flushThreads.length;
        if (len == 1) {
            return flushThreads[0].add(item);
        }

        int mod = index.incrementAndGet() % len;
        return flushThreads[mod].add(item);

    }


    private static class FlushThread<Item> implements Runnable {

        private String name;

        //队列大小
        private final int bufferSize;

        //操作间隔
        private final int flushInterval;

        //上一次提交的时间。
        private volatile long lastFlushTime;
        private volatile Thread writer;

        /**
         * 持有数据的阻塞队列
         * <p>
         * 既然我们的系统是在高并发的环境下使用，那我们肯定不能使用，
         * 普通的ArrayList来持有。我们可以使用阻塞队列来持有需要合并的请求。
         */
        private final BlockingQueue<Item> queue;

        //达成条件后具体执行的方法
        private final Processor<Item> processor;

        //构造函数
        public FlushThread(String name, int bufferSize, int flushInterval, int queueSize, Processor<Item> processor) {
            this.name = name;
            this.bufferSize = bufferSize;
            this.flushInterval = flushInterval;
            this.lastFlushTime = System.currentTimeMillis();
            this.processor = processor;

            this.queue = new ArrayBlockingQueue<>(queueSize);

        }

        //外部提交数据的方法
        public boolean add(Item item) {
            boolean result = queue.offer(item);
            flushOnDemand();
            return result;
        }

        /**
         * 数据结构还需要提供一个timeOut()的方法，外部有一个计时器定时调用这个timeOut方法，如果方法被调用，则直接向远程提交数据。
         */
        //提供给外部的超时方法
        public void timeOut() {
            //超过两次提交超过时间间隔
            if (System.currentTimeMillis() - lastFlushTime >= flushInterval) {
                start();
            }
        }


        /**
         * 条件满足的时候线程执行提交动作，条件不满足的时候线程应当暂停，等待队列达到提交数据的条件。
         * 所以我们可以考虑使用 LockSupport.park()和LockSupport.unpark 来暂停和激活操作线程。
         */
        //解除线程的阻塞
        private void start() {
            LockSupport.unpark(writer);
        }

        //当前的数据是否大于提交的条件
        private void flushOnDemand() {
            if (queue.size() >= bufferSize) {
                start();
            }
        }

        //执行提交数据的方法
        public void flush() {
            lastFlushTime = System.currentTimeMillis();
            List<Item> temp = new ArrayList<>(bufferSize);
            int size = queue.drainTo(temp, bufferSize);
            if (size > 0) {
                try {
                    processor.process(temp);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }

        //根据数据的尺寸和时间间隔判断是否提交
        private boolean canFlush() {
            return queue.size() > bufferSize || System.currentTimeMillis() - lastFlushTime > flushInterval;
        }

        @Override
        public void run() {
            writer = Thread.currentThread();
            writer.setName(name);

            while (!writer.isInterrupted()) {
                while (!canFlush()) {
                    //如果线程没有被打断，且不达到执行的条件，则阻塞线程
                    LockSupport.park(this);
                }
                flush();
            }

        }
    }


    public static void main(String[] args) throws InterruptedException {

        Flusher<String> stringFlusher = new Flusher<>("test", 5, 1000, 30, 1, new PrintOutProcessor());

        int index = 1;
        while (true) {
            stringFlusher.add(String.valueOf(index++));
            Thread.sleep(1000);
        }
    }

}
