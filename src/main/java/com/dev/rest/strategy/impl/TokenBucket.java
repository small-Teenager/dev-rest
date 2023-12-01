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
/**
 * 令牌桶限流 需要初始化令牌桶，往令牌桶中添加令牌
 *
 * redisTemplate.opsForList().rightPush(combineKey,UUID.randomUUID().toString());
 */
@Service(value = "TOKEN_BUCKET")
public class TokenBucket implements RedisLimitStrategy {
    private static final Logger log = LoggerFactory.getLogger(RedisLimitStrategy.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void process(JoinPoint point, RedisLimit redisLimit) {
        String combineKey = getCombineKey(redisLimit, point);
        try {
            Object result = redisTemplate.opsForList().leftPop(combineKey);
            if (result == null) {
                // 无令牌
                throw new RedisLimitException("访问过于频繁，请稍候再试");
            }
        } catch (RedisLimitException e) {
            throw e;
        } catch (Exception e) {
            log.error("服务器限流-令牌桶限流异常，请稍候再试");
            e.printStackTrace();
        }
    }
}
