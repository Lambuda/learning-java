package com.lin.ds.itms.thread;

public class ThreadVolatile {

    static class MyObject{
        private String name ="";
        private volatile int value= 0;
        public void add(int value){
            this.value += value;
        }
    }

    public static void main(String[] args) throws Exception{
        MyObject obj = new MyObject();

        Thread t1 = new Thread(() ->{
            try {

                obj.add(1);
                Thread.sleep(2000);
                obj.name ="x";
            }catch (Exception e){}
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            System.out.println(obj.name + "222");
            System.out.println(obj.value);
            try {
                Thread.sleep(3000);
                System.out.println(obj.name + "333");
            }catch (Exception e){}
        });
        t2.start();
      //  t2.join();
    }

}
