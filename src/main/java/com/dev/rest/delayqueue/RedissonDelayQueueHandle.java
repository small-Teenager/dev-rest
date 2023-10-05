package com.dev.rest.delayqueue;

/**
 * RedissonDelayQueueHandle
 * @param <T>
 */
public interface RedissonDelayQueueHandle<T> {
    void execute(T t);
}
