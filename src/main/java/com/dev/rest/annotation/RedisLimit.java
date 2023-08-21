package com.dev.rest.annotation;


import com.dev.rest.enums.RedisLimitType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLimit {
    /**
     * 限流key
     */
    String key() default "rate_limit:";

    /**
     * 限流时间,单位秒
     */
    long time() default 60;

    /**
     * 限流次数 单位时间限制通过的请求数
     */
    int count() default 100;

    /**
     * 限流类型
     */
    RedisLimitType limitType() default RedisLimitType.DEFAULT;
}
