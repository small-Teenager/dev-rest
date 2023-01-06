package com.redis.rest.controller.apply;

import com.redis.rest.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

/**
 * @author: yaodong zhang
 * @create 2023/1/5
 */
@RestController
@RequestMapping("/apply/lock")
public class LockController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostMapping("/simple/{id}")
    public ApiResponse<String> simpleLock(@PathVariable(value = "id") @NotNull String id) {
        String key = "lock:simple:" + id;
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "", 60, TimeUnit.SECONDS);
        if (flag) {
            // 加锁成功
            return ApiResponse.success("获取锁成功");
        }
        return ApiResponse.error(404,"获取锁失败");
    }
}
