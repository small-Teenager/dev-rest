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
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 动态时间窗口限流
 */
@Service(value = "DYNAMIC_TIME_WINDOW")
public class DynamicTimeWindow implements RedisLimitStrategy {
    private static final Logger log = LoggerFactory.getLogger(DynamicTimeWindow.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void process(JoinPoint point, RedisLimit redisLimit) {
        long time = redisLimit.time();
        int maxCount = redisLimit.maxCount();
        long currentTime = System.currentTimeMillis();
        String combineKey = getCombineKey(redisLimit, point);

        long ms = TimeUnit.MILLISECONDS.convert(time, redisLimit.timeUnit());
        try {
            Set set = redisTemplate.opsForZSet().rangeByScore(combineKey, currentTime - ms, currentTime);
            if(!CollectionUtils.isEmpty(set)){
                int number = set.size();
                if (number >= maxCount) {
                    throw new RedisLimitException("访问过于频繁，请稍候再试");
                }
                log.info("限制请求次数:{},当前请求次数:{},缓存key:{}", maxCount, number, combineKey);
            }
        } catch (RedisLimitException e) {
            throw e;
        } catch (Exception e) {
            log.error("服务器限流-动态时间窗口限流异常，请稍候再试");
            e.printStackTrace();
        }

        redisTemplate.opsForZSet().add(combineKey, UUID.randomUUID().toString(), currentTime);
    }


}
