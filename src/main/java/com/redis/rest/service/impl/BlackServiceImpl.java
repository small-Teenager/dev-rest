package com.redis.rest.service.impl;

import com.redis.rest.dto.AddBlackDTO;
import com.redis.rest.service.BlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author: yaodong zhang
 * @create 2023/1/3
 */
@Service
public class BlackServiceImpl implements BlackService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Boolean addBlack(AddBlackDTO record) {
        String key = "black";
        return redisTemplate.opsForSet().add(key, record.getMobile()) > 0;
    }

    @Override
    public Boolean isBlack(String mobile) {
        String key = "black";
        return redisTemplate.opsForSet().isMember(key, mobile);
    }

    @Override
    public Boolean removeBlack(String mobile) {
        String key = "black";
        return redisTemplate.opsForSet().remove(key, mobile) > 0;
    }
}
