package com.dev.rest.enums;

/**
 * 限流策略
 */
public enum RedisLimitStrategyEnum {
    /**
     * 计数器限流
     * counter
     */
    COUNTER,
    /**
     * 动态时间窗口限流
     * Dynamic Time Window
     */
    DYNAMIC_TIME_WINDOW,

    /**
     * 令牌桶限流
     * Token Bucket
     */
    TOKEN_BUCKET
    ;

    RedisLimitStrategyEnum() {
    }

}
