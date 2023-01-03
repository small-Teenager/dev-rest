package com.redis.rest.service.impl;

import com.redis.rest.dto.SMoveDTO;
import com.redis.rest.dto.SetSAddDTO;
import com.redis.rest.service.SetRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author: yaodong zhang
 * @create 2022/12/23
 */
@Service
public class SetRedisServiceImpl implements SetRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Boolean sadd(SetSAddDTO record) {
        return redisTemplate.opsForSet().add(record.getKey(),record.getValue())>0;
    }

    @Override
    public Boolean sismember(String key, String member) {
        return redisTemplate.opsForSet().isMember(key,member);
    }

    @Override
    public Object spop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    @Override
    public Object srandmember(String key, Long count) {
        return redisTemplate.opsForSet().randomMembers(key,count);
    }

    @Override
    public Long srem(String key, String member) {
        return redisTemplate.opsForSet().remove(key,member);
    }

    @Override
    public Boolean smove(SMoveDTO record) {
        return redisTemplate.opsForSet().move(record.getSource(),record.getMember(),record.getDestination());
    }

    @Override
    public Set members(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public Long size(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public Set difference(String key,String otherKey) {
        return redisTemplate.opsForSet().difference(key,otherKey);
    }

    @Override
    public Boolean differenceAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key,otherKey,destKey)>0;
    }

    @Override
    public Set intersect(String key, String otherKey) {
        return redisTemplate.opsForSet().intersect(key,otherKey);
    }

    @Override
    public Boolean intersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key,otherKey,destKey)>0;
    }

    @Override
    public Set union(String key, String otherKey) {
        return redisTemplate.opsForSet().union(key,otherKey);
    }

    @Override
    public Boolean unionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key,otherKey,destKey)>0;
    }

}
