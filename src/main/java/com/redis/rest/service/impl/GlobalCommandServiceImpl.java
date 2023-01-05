package com.redis.rest.service.impl;

import com.redis.rest.service.GlobalCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: yaodong zhang
 * @create 2022/12/27
 */
@Service
public class GlobalCommandServiceImpl implements GlobalCommandService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Boolean expire(String key,Long timeout) {
        return redisTemplate.expire(key,timeout, TimeUnit.SECONDS);
    }

    @Override
    public Boolean exist(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public DataType type(String key) {
        return redisTemplate.type(key);
    }

    @Override
    public Boolean rename(String key, String newKey) {
        return redisTemplate.renameIfAbsent(key,newKey);
    }

    @Override
    public Set keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
