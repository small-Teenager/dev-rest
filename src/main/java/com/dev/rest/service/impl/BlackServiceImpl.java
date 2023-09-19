package com.dev.rest.service.impl;

import com.alibaba.fastjson.JSON;
import com.dev.rest.dto.AddBlackDTO;
import com.dev.rest.entity.Black;
import com.dev.rest.mapper.BlackMapper;
import com.dev.rest.service.BlackService;
import com.dev.rest.utils.GeneratePhoneNumber;
import com.dev.rest.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private IdWorker idWorker;

    @Override
    public Boolean addBlack(AddBlackDTO record) {
        Boolean result = redisTemplate.opsForSet().add(BLACKLIST, record.getMobile()) > 0;
        if(result){
            Black black = new Black();
            black.setMobile(GeneratePhoneNumber.generatePhoneNumber());
            black.setId(idWorker.nextId());
            blackMapper.insert(black);
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
        int len=3;
        String mobiles[]= new String[len];
        for (int i = 0; i < len; i++) {
            Black black = new Black();
            String mobile = GeneratePhoneNumber.generatePhoneNumber();
            mobiles[i]=mobile;
            black.setMobile(mobile);
            black.setId(idWorker.nextId());
            blackMapper.insert(black);
        }

        redisTemplate.opsForSet().add(BLACKLIST,mobiles);
        return true;
    }

    @Override
    public List<String> selectAll() {
        return blackMapper.selectAll();
    }

    @Override
    public Black selectByMobile(String mobile) {
        return blackMapper.selectByMobile(mobile);
    }
}
