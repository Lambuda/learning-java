package com.haier.rrswl.itms.aspect;

import com.haier.rrswl.itms.annotation.RateLimit;
import com.haier.rrswl.itms.config.RedisClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * 拦截器
 *
 * @Author: Lin
 * @CreateDate: 2019/3/4 17:42
 */
@Aspect
@Configuration
public class LimitAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LimitAspect.class);

    @Resource
    private RedisClient redisClient;

    @Around("execution(* com.haier.rrswl.itms.controller ..*(..) )")
    public Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);
        if (rateLimit != null) {
            boolean flag = redisClient.rateLimit(rateLimit.key(), rateLimit.count(), rateLimit.time());
            if (flag) throw new RuntimeException("已达到限流次数 拒绝继续访问！");
            LOG.info("未达到限流次数 继续访问");
            return joinPoint.proceed();
        }
        return joinPoint.proceed();
    }

}
