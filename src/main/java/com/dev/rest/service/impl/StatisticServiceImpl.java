package com.dev.rest.service.impl;

import com.dev.rest.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: yaodong zhang
 * @create 2022/12/30
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Long dailyActiveUser() {
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMMdd"));
        // 日活
        String key = "dau"  + keySuffix;
        return (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
    }

    @Override
    public Long dailyActiveUser(String day) {
        // 日活
        String key = "dau:"  + day;
        return (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
    }

    @Override
    public Boolean addDailyActive(Long userId) {

        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMMdd"));
        // 日活
        String key = "dau"  + keySuffix;
        // userId 和偏移量转换
        return redisTemplate.opsForValue().setBit(key, userId.hashCode(), true);
    }

    @Override
    public Boolean isActiveUser(String day, Long userId) {
        // id需要减去一个固定的偏移量
        String key = "dau:"  + day;
        return redisTemplate.opsForValue().getBit(key,userId);
    }
}
