package com.dev.rest.enums;

public enum RedisLimitTypeEnum {
    DEFAULT,
    /**
     * 请求IP限流
     */
    IP,
    /**
     * 方法级别限流
     * key = ClassName+MethodName
     */
    METHOD,

    /**
     * uri级别限流
     * key = uri
     */
    URI
    ;

    RedisLimitTypeEnum() {
    }
}
