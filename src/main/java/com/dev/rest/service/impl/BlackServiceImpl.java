package com.dev.rest.service.impl;

import com.dev.rest.dto.AddBlackDTO;
import com.dev.rest.mapper.BlackMapper;
import com.dev.rest.service.BlackService;
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

    @Autowired
    private BlackMapper blackMapper;

    private static final String BLACKLIST = "blacklist";

    @Override
    public Boolean addBlack(AddBlackDTO record) {
        Boolean result = redisTemplate.opsForSet().add(BLACKLIST, record.getMobile()) > 0;
        if(result){
            blackMapper.insert(record.getMobile());
        }
        return result;
    }

    @Override
    public Boolean isBlack(String mobile) {
        Boolean result = redisTemplate.opsForSet().isMember(BLACKLIST, mobile);
        if(!result){
            result = blackMapper.exist(mobile) > 0;
        }
        return result;
    }

    @Override
    public Boolean removeBlack(String mobile) {
        Boolean result = redisTemplate.opsForSet().remove(BLACKLIST, mobile) > 0;
        if(result){
            blackMapper.delete(mobile);
        }
        return result;
    }

    @Override
    public Boolean init() {
        blackMapper.insert("1357924680");
        blackMapper.insert("13277522414");
        blackMapper.insert("13914744236");
        redisTemplate.opsForSet().add(BLACKLIST,"1357924680","13277522414","13914744236");
        return true;
    }
}
