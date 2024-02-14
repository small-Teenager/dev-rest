package com.dev.rest.controller.apply;

import com.dev.rest.enums.RedisLimitStrategyEnum;
import com.dev.rest.enums.RedisLimitTypeEnum;
import com.dev.rest.annotation.RedisLimit;
import com.dev.rest.response.ApiResponse;
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


    @GetMapping("/counter")
    @RedisLimit(prefix = "limit", maxCount = 3)
    public ApiResponse<String> limit() {
        return ApiResponse.success("hello world");
    }

    @GetMapping("/dynamic")
    @RedisLimit(prefix = "limit", maxCount = 3, limitStrategy = RedisLimitStrategyEnum.DYNAMIC_TIME_WINDOW)
    public ApiResponse<String> dynamic() {
        return ApiResponse.success("hello world");
    }

    @GetMapping("/token-bucket")
    @RedisLimit(prefix = "limit", maxCount = 3, limitStrategy = RedisLimitStrategyEnum.TOKEN_BUCKET)
    public ApiResponse<String> tokenBucket() {
        return ApiResponse.success("hello world");
    }

    @GetMapping("/ip-limit")
    @RedisLimit(prefix = "ip-limit", maxCount = 3, limitType = RedisLimitTypeEnum.URI)
    public ApiResponse<String> ipLimit() {
        return ApiResponse.success("hello world");
    }

}
