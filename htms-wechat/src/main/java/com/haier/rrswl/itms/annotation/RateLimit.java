package com.haier.rrswl.itms.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限流 注解
 *
 * @Author: Lin
 * @CreateDate: 2019/3/4 14:57
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * 限流唯一标示
     *
     * @return
     */
    String key() default "";

    /**
     * 限流时间 单位 秒
     *
     * @return
     */
    String time();

    /**
     * 限流次数
     *
     * @return
     */
    String count();

}
