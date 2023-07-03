package com.redis.rest.service.impl;

import com.redis.rest.dto.*;
import com.redis.rest.service.StringRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeUnit;


/**
 * @author: yaodong zhang
 * @create 2022/12/17
 */
@Service
public class StringRedisServiceImpl implements StringRedisService {

    private static final Logger log = LoggerFactory.getLogger(StringRedisService.class);

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

    @Override
    public Long multiplication(MultiplicationDTO record) {

        // 执行 lua 脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        // 指定 lua 脚本
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("/script/multiplication.lua")));
        if(log.isDebugEnabled()){
            log.info(redisScript.getScriptAsString());
        }
        // 指定返回类型
        redisScript.setResultType(Long.class);
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        Long result = stringRedisTemplate.execute(redisScript, Collections.singletonList(record.getKey()), String.valueOf(record.getDelta()));
        return result;
    }
}
