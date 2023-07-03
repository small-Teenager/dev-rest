package com.redis.rest.controller.basic;

import com.redis.rest.dto.*;
import com.redis.rest.response.ApiResponse;
import com.redis.rest.service.StringRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 内部编码
 * int : 8个字节的长整型
 * embster : 小于等于39个字节的字符串
 * raw : 大于39个字节的字符串
 * string 基本操作
 *
 * @author: yaodong zhang
 * @create 2022/12/17
 */
@RestController
@RequestMapping("/basic/string")
public class StringController {

    @Autowired
    private StringRedisService stringRedisService;

    @GetMapping("/{key}")
    public ApiResponse<String> get(@PathVariable(value = "key") @NotNull String key) {
        String result = stringRedisService.get(key);
        return ApiResponse.success(result);
    }

    @PostMapping("")
    public void set(@Validated @RequestBody StringSetDTO record) {
        stringRedisService.set(record);
    }

    @DeleteMapping("/{key}")
    public ApiResponse<Boolean> delete(@PathVariable(value = "key") @NotNull String key) {
        Boolean result = stringRedisService.delete(key);
        return ApiResponse.success(result);
    }

    @PostMapping("/expire")
    public ApiResponse<Boolean> expire(@Validated @RequestBody ExpireDTO record) {
        Boolean result = stringRedisService.expire(record);
        return ApiResponse.success(result);
    }


    @PostMapping("/incr")
    public ApiResponse<Boolean> incr(@Validated @RequestBody IncrDTO record) {
        return ApiResponse.success(stringRedisService.incr(record));
    }

    @PostMapping("/decr")
    public ApiResponse<Boolean> decr(@Validated @RequestBody DecrDTO record) {
        return ApiResponse.success(stringRedisService.decr(record));
    }

    @PostMapping("/multiplication")
    public ApiResponse<Long> multiplication(@Validated @RequestBody MultiplicationDTO record) {
        return ApiResponse.success(stringRedisService.multiplication(record));
    }

    @GetMapping("/len/{key}")
    public ApiResponse<Long> len(@PathVariable(value = "key") @NotNull String key ) {
        return ApiResponse.success(stringRedisService.len(key));
    }


    @PostMapping("/append")
    public ApiResponse<Boolean> append(@Validated @RequestBody AppendDTO record) {
        return ApiResponse.success(stringRedisService.append(record));
    }

}
