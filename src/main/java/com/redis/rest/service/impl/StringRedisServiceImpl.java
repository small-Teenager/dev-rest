package com.redis.rest.service.impl;

import com.redis.rest.dto.AppendDTO;
import com.redis.rest.dto.DecrDTO;
import com.redis.rest.dto.ExpireDTO;
import com.redis.rest.dto.IncrDTO;
import com.redis.rest.dto.StringSetDTO;
import com.redis.rest.service.StringRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * @author: yaodong zhang
 * @create 2022/12/17
 */
@Service
public class StringRedisServiceImpl implements StringRedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(StringSetDTO record) {
        stringRedisTemplate.opsForValue().set(record.getKey(),record.getValue(),record.getTimeout(),TimeUnit.SECONDS);
    }

    @Override
    public Boolean incr(IncrDTO record) {
       return stringRedisTemplate.opsForValue().increment(record.getKey(),record.getDelta())>0;
    }

    @Override
    public Boolean decr(DecrDTO record) {
        return stringRedisTemplate.opsForValue().decrement(record.getKey(),record.getDelta())>0;
    }

    @Override
    public Long len(String key) {
        return stringRedisTemplate.opsForValue().size(key);
    }

    @Override
    public Boolean append(AppendDTO record) {
        return stringRedisTemplate.opsForValue().append(record.getKey(),record.getValue())>0;
    }

    @Override
    public Boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }

    @Override
    public Boolean expire(ExpireDTO record) {
        return stringRedisTemplate.expire(record.getKey(),record.getTimeout(), TimeUnit.SECONDS);
    }
}
