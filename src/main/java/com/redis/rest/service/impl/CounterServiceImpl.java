package com.redis.rest.service.impl;

import com.redis.rest.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: yaodong zhang
 * @create 2023/1/7
 */
@Service
public class CounterServiceImpl implements CounterService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Boolean fanIncr(String userId) {
        String counterKey = "counter:" + userId;
        return redisTemplate.opsForHash().increment(counterKey, "fans", 1) > 0;
    }

    @Override
    public Boolean concernIncr(String userId) {
        String counterKey = "counter:" + userId;
        return redisTemplate.opsForHash().increment(counterKey, "concerns", 1) > 0;
    }

    @Override
    public Boolean likedIncr(String userId) {
        String counterKey = "counter:" + userId;
        return redisTemplate.opsForHash().increment(counterKey, "liked", 1) > 0;
    }

    @Override
    public Map counterMap(String userId) {
        String counterKey = "counter:" + userId;
        return redisTemplate.opsForHash().entries(counterKey);
    }

    @Override
    public Boolean fanDecr(String userId) {
        String counterKey = "counter:" + userId;
        Object obj =  redisTemplate.opsForHash().get(counterKey, "fans");
        Long fans = Long.valueOf(String.valueOf(obj));
        if (fans > 0) {
            return redisTemplate.opsForHash().increment(counterKey, "fans", -1) > 0;
        }
        // 抛异常 粉丝数最小为0
        return false;
    }

    @Override
    public Boolean concernDecr(String userId) {
        String counterKey = "counter:" + userId;
        Object obj = redisTemplate.opsForHash().get(counterKey, "concerns");
        Long concerns = Long.valueOf(String.valueOf(obj));
        if (concerns > 0) {
            return redisTemplate.opsForHash().increment(counterKey, "concerns", -1) > 0;
        }
        // 抛异常 关注数最小为0
        return false;
    }
}
