package com.dev.rest.strategy.impl;

import com.dev.rest.annotation.RedisLimit;
import com.dev.rest.exception.RedisLimitException;
import com.dev.rest.strategy.RedisLimitStrategy;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 动态时间窗口限流
 */
@Service(value = "DYNAMIC_TIME_WINDOW")
public class DynamicTimeWindow implements RedisLimitStrategy {
    private static final Logger log = LoggerFactory.getLogger(RedisLimitStrategy.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void process(JoinPoint point, RedisLimit redisLimit) {
        long time = redisLimit.time();
        int count = redisLimit.count();
        long currentTime = System.currentTimeMillis();
        String combineKey = getCombineKey(redisLimit, point);

        long ms = TimeUnit.MILLISECONDS.convert(time, redisLimit.timeUnit());
        try {
            int number = redisTemplate.opsForZSet().rangeByScore(combineKey, currentTime - ms, currentTime).size();
            if (number >= count) {
                throw new RedisLimitException("访问过于频繁，请稍候再试");
            }
            log.info("限制请求次数:{},当前请求次数:{},缓存key:{}", count, number, combineKey);
        } catch (RedisLimitException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("服务器限流异常，请稍候再试");
        }

        redisTemplate.opsForZSet().add(combineKey, UUID.randomUUID().toString(), currentTime);
    }


}
