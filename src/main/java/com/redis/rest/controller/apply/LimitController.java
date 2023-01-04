package com.redis.rest.controller.apply;

import com.redis.rest.annotation.RedisLimit;
import com.redis.rest.enums.RedisLimitType;
import com.redis.rest.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yaodong zhang
 * @create 2023/1/4
 */
@RestController
@RequestMapping("/apply/limit")
public class LimitController {


    @GetMapping("")
    @RedisLimit(key = "limit", count = 3)
    public ApiResponse<String> limit() {
        return ApiResponse.success("hello world");
    }

    @GetMapping("/ip-limit")
    @RedisLimit(key = "ip-limit", count = 3, limitType = RedisLimitType.IP)
    public ApiResponse<String> ipLimit() {
        return ApiResponse.success("hello world");
    }

}
