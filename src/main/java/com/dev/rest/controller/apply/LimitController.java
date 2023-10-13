package com.dev.rest.controller.apply;

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


    @GetMapping("")
    @RedisLimit(prefix = "limit", count = 3)
    public ApiResponse<String> limit() {
        return ApiResponse.success("hello world");
    }

    @GetMapping("/ip-limit")
    @RedisLimit(prefix = "ip-limit", count = 3, limitType = RedisLimitTypeEnum.IP)
    public ApiResponse<String> ipLimit() {
        return ApiResponse.success("hello world");
    }

}
