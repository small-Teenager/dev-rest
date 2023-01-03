package com.redis.rest.service.impl;

import com.redis.rest.dto.IncrementScoreDTO;
import com.redis.rest.dto.SortSetZAddDTO;
import com.redis.rest.service.SortSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author: yaodong zhang
 * @create 2022/12/27
 */
@Service
public class SortSetServiceImpl implements SortSetService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Boolean zadd(SortSetZAddDTO record) {
        return redisTemplate.opsForZSet().add(record.getKey(),record.getValue(),record.getScore());
    }

    @Override
    public Double incrementScore(IncrementScoreDTO record) {
        return redisTemplate.opsForZSet().incrementScore(record.getKey(),record.getValue(),record.getScore());
    }

    @Override
    public Double score(String key, String object) {
        return redisTemplate.opsForZSet().score(key,object);
    }

    @Override
    public Long size(String key) {
//        redisTemplate.opsForZSet().zCard(key)
        return redisTemplate.opsForZSet().size(key);
    }

    @Override
    public Long count(String key, Double min, Double max) {
        return redisTemplate.opsForZSet().count(key,min,max);
    }

    @Override
    public Set range(String key, Long start, Long end) {
        return redisTemplate.opsForZSet().range(key,start,end);
    }

    @Override
    public Set rangeByScore(String key, Double min, Double max) {
        return redisTemplate.opsForZSet().rangeByScore(key,min,max);
    }

    @Override
    public Long rank(String key, String object) {
        return redisTemplate.opsForZSet().rank(key,object);
    }

    @Override
    public Long reverseRank(String key, String object) {
        return redisTemplate.opsForZSet().reverseRank(key,object);
    }

    @Override
    public Set reverseRange(String key, Long start, Long end) {
        return redisTemplate.opsForZSet().reverseRange(key,start,end);
    }

    @Override
    public Boolean remove(String key, String object) {
        return redisTemplate.opsForZSet().remove(key,object)>0;
    }

    @Override
    public Boolean removeRange(String key, Long start, Long end) {
        return redisTemplate.opsForZSet().removeRange(key,start,end)>0;
    }

    @Override
    public Boolean removeRangeByScore(String key, Double min, Double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key,min,max)>0;
    }
}
