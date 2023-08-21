package com.dev.rest.service.impl;

import com.dev.rest.dto.LPushDTO;
import com.dev.rest.dto.LRemDTO;
import com.dev.rest.dto.RpushDTO;
import com.dev.rest.service.ListRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author: yaodong zhang
 * @create 2022/12/19
 */
@Service
public class ListRedisServiceImpl implements ListRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Boolean lPush(LPushDTO record) {
       return redisTemplate.opsForList().leftPush(record.getKey(), record.getValue())>0;
    }

    @Override
    public String lpop(String key) {
        return (String) redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public Boolean rpush(RpushDTO record) {
        return redisTemplate.opsForList().rightPush(record.getKey(), record.getValue())>0;
    }

    @Override
    public String rPop(String key) {
        return (String) redisTemplate.opsForList().rightPop(key);
    }

    @Override
    public Boolean lRem(LRemDTO record) {
        return  redisTemplate.opsForList().remove(record.getKey(),record.getCount(),record.getValue())>0;
    }

    @Override
    public Long lLen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public String lindex(String key, Long index) {
        return (String) redisTemplate.opsForList().index(key,index);
    }

    @Override
    public void ltrim(String key,long start,long end) {
         redisTemplate.opsForList().trim(key,start,end);
    }

    @Override
    public void lset(String key, Long index, String value) {
         redisTemplate.opsForList().set(key,index,value);
    }


}
