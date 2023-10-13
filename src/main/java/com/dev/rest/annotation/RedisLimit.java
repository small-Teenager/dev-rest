package com.dev.rest.annotation;


import com.dev.rest.enums.RedisLimitTypeEnum;

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
     * 限流key 前缀
     */
    String prefix() default "rate_limit:";

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
    RedisLimitTypeEnum limitType() default RedisLimitTypeEnum.DEFAULT;
}
