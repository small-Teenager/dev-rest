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

    private static final String BLACKLIST = "blacklist";

    @Override
    public Boolean addBlack(AddBlackDTO record) {
        return redisTemplate.opsForSet().add(BLACKLIST, record.getMobile()) > 0;
    }

    @Override
    public Boolean isBlack(String mobile) {
        return redisTemplate.opsForSet().isMember(BLACKLIST, mobile);
    }

    @Override
    public Boolean removeBlack(String mobile) {
        return redisTemplate.opsForSet().remove(BLACKLIST, mobile) > 0;
    }
}
