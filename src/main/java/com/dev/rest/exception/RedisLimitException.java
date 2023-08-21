package com.dev.rest.exception;

/**
 * @author: yaodong zhang
 * @create 2023/1/4
 */
public class RedisLimitException extends AbstractException{
    public RedisLimitException(String code, String message) {
        super(code, message);
    }
    public RedisLimitException(String message) {
        super("404",message);
    }
}
