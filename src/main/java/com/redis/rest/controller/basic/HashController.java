package com.redis.rest.controller.basic;

import com.redis.rest.dto.HSetDTO;
import com.redis.rest.response.ApiResponse;
import com.redis.rest.service.HashService;
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
import java.util.Map;

/**
 * 哈希表
 *
 * @author: yaodong zhang
 * @create 2022/12/27
 */
@RestController
@RequestMapping("/basic/hash")
public class HashController {

    @Autowired
    private HashService hashService;


    @PostMapping("/hset")
    public ApiResponse<Boolean> hset(@Validated @RequestBody HSetDTO record) {
        hashService.hset(record);
        return ApiResponse.success(true);
    }

    @GetMapping("/hget/{key}/{hashKey}")
    public ApiResponse<String> hget(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "hashKey") @NotNull String hashKey) {
        String result = hashService.hget(key, hashKey);
        return ApiResponse.success(result);
    }

    @GetMapping("/entries/{key}")
    public ApiResponse<Map> entries(@PathVariable(value = "key") @NotNull String key) {
        Map result = hashService.entries(key);
        return ApiResponse.success(result);
    }

    @DeleteMapping("/hdel/{key}/{hashKey}")
    public ApiResponse<Boolean> hdel(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "hashKey") @NotNull String hashKey) {
        Boolean result = hashService.hdel(key,hashKey);
        return ApiResponse.success(result);
    }

    @GetMapping("/hlen/{key}")
    public ApiResponse<Long> hlen(@PathVariable(value = "key") @NotNull String key) {
        Long result = hashService.hlen(key);
        return ApiResponse.success(result);
    }

    @GetMapping("/hexists/{key}/{hashKey}")
    public ApiResponse<Boolean> hexists(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "hashKey") @NotNull String hashKey) {
        Boolean result = hashService.hexists(key,hashKey);
        return ApiResponse.success(result);
    }
}
