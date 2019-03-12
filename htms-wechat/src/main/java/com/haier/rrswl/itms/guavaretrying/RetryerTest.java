package com.haier.rrswl.itms.guavaretrying;


import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import com.haier.rrswl.itms.listener.RetryLogListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 重试器测试
 *
 * @Author: Lin
 * @CreateDate: 2019/3/6 11:03
 */
@Slf4j
public class RetryerTest {

    public static boolean test() throws Exception {
        //定义重试机制
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                //retryIf 重试条件
                .retryIfException()
                .retryIfRuntimeException()
                .retryIfExceptionOfType(Exception.class)
                .retryIfException(Predicates.equalTo(new Exception()))
                .retryIfResult(Predicates.equalTo(false))

                //等待策略：每次请求间隔1s
                .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))

                //停止策略 : 尝试请求6次
                .withStopStrategy(StopStrategies.stopAfterAttempt(6))

                //时间限制 : 某次请求不得超过2s , 类似: TimeLimiter timeLimiter = new SimpleTimeLimiter();
                .withAttemptTimeLimiter(AttemptTimeLimiters.fixedTimeLimit(2, TimeUnit.SECONDS))
                //.withRetryListener(new RetryLogListener())

                .build();

        //定义请求实现
        Callable<Boolean> callable = new Callable<Boolean>() {
            int count = 1;

            @Override
            public Boolean call() throws Exception {
                //log.info("call count=" + count);
                count++;

                if (count == 2) {
                    throw new NullPointerException();
                } else if (count == 3) {
                    throw new Exception();
                } else if (count == 4) {
                    throw new RuntimeException();
                } else if (count == 5) {
                    return false;
                } else {
                    return true;
                }

            }
        };
        //利用重试器调用请求

        return retryer.call(callable);
    }

    public static void main(String[] args) throws Exception {


        test();
    }
}
