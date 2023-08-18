package com.redis.rest.controller;

import com.redis.rest.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author: yaodong zhang
 * @create 2023/1/16
 */
@RestController
@RequestMapping("/script")
public class ScriptController {

    private static final Logger log = LoggerFactory.getLogger(ScriptController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @DeleteMapping("/{key}")
    public ApiResponse<Boolean> delKey(@PathVariable(value = "key") @NotNull String key) {

        String UUID = java.util.UUID.randomUUID().toString();
        boolean success = stringRedisTemplate.opsForValue().setIfAbsent(key, UUID, 3, TimeUnit.MINUTES);
        if (!success) {
//            System.err.println("锁已存在");
            log.info("锁已存在");
        }
        // 执行 lua 脚本
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        // 指定 lua 脚本
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("/script/del_key.lua")));

        // 预加载脚本（可选）
//        stringRedisTemplate.getConnectionFactory().getConnection().scriptLoad(redisScript.getScriptAsString().getBytes());

        // 指定返回类型
        redisScript.setResultType(Boolean.class);
        log.info(redisScript.getScriptAsString());
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        Boolean result = stringRedisTemplate.execute(redisScript, Collections.singletonList(key), UUID);
        return ApiResponse.success(result);
    }

    @GetMapping("/limit")
    public ApiResponse<Long> limit() {
        String key = "123";
        // 执行 lua 脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        // 指定 lua 脚本
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("/script/counter_limit.lua")));
        log.info(redisScript.getScriptAsString());
        // 指定返回类型
        redisScript.setResultType(Long.class);
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        Long result = stringRedisTemplate.execute(redisScript, Collections.singletonList(key), "10", "60");
        if (result == -1) {
            log.info("访问过于频繁，请稍候再试");
        }
        return ApiResponse.success(result);
    }

}
