package com.redis.rest.service.impl;

import com.redis.rest.dto.PFAddDTO;
import com.redis.rest.dto.PFMergeDTO;
import com.redis.rest.service.HyperLogLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author: yaodong zhang
 * @create 2022/12/30
 */
@Service
public class HyperLogLogImpl implements HyperLogLogService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Boolean pfadd(PFAddDTO record) {
        return redisTemplate.opsForHyperLogLog().add(record.getKey(),record.getValue())>0;
    }

    @Override
    public Long pfcount(String key) {
        return redisTemplate.opsForHyperLogLog().size(key);
    }

    @Override
    public Boolean pfmerge(PFMergeDTO record) {
        return redisTemplate.opsForHyperLogLog().union(record.getDestination(),record.getSourceKey1(),record.getSourceKey2())>0;
    }
}
