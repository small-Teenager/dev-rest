package com.redis.rest.service.impl;

import com.redis.rest.dto.HSetDTO;
import com.redis.rest.service.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: yaodong zhang
 * @create 2022/12/28
 */
@Service
public class HashServiceImpl implements HashService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void hset(HSetDTO record) {
        redisTemplate.opsForHash().put(record.getKey(),record.getHashKey(),record.getValue());
    }

    @Override
    public String hget(String key, String hashKey) {

      return (String) redisTemplate.opsForHash().get(key,hashKey);
    }

    @Override
    public Map entries(String key) {
        return  redisTemplate.opsForHash().entries(key);
    }

    @Override
    public Boolean hdel(String key, String hashKey) {
        return redisTemplate.opsForHash().delete(key,hashKey)>0;
    }

    @Override
    public Long hlen(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    @Override
    public Boolean hexists(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key,hashKey);
    }
}
