package com.dev.rest.factory.impl;

import com.dev.rest.annotation.RedisLimit;
import com.dev.rest.exception.RedisLimitException;
import com.dev.rest.factory.RedisLimitStrategy;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 计数器限流
 */
@Service(value = "COUNTER")
public class Counter implements RedisLimitStrategy {

    private static final Logger log = LoggerFactory.getLogger(RedisLimitStrategy.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void process(JoinPoint point, RedisLimit redisLimit) {

        long time = redisLimit.time();
        int count = redisLimit.count();

        String combineKey = getCombineKey(redisLimit, point);
        try {
            Long countReq = redisTemplate.opsForValue().increment(combineKey);
            if (countReq == 1) {
                // 值为1说明之前不存在该值, 因此需要设置其过期时间
                redisTemplate.expire(combineKey, time, TimeUnit.SECONDS);
            }
            if (log.isDebugEnabled()) {
                log.debug("combineKey:{}", combineKey);
            }
            if (countReq.intValue() > count) {
                throw new RedisLimitException("访问过于频繁，请稍候再试");
            }
            log.info("限制请求次数:{},当前请求次数:{},缓存key:{}", count, countReq, combineKey);
        } catch (RedisLimitException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("服务器限流异常，请稍候再试");
        }

    }
}
