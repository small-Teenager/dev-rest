package com.redis.rest.controller.other;

import com.redis.rest.dto.PFAddDTO;
import com.redis.rest.dto.PFMergeDTO;
import com.redis.rest.response.ApiResponse;
import com.redis.rest.service.HyperLogLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author: yaodong zhang
 * @create 2022/12/30
 */
@RestController
@RequestMapping("/other/hyper-log")
public class HyperLogLogController {

    @Autowired
    private HyperLogLogService hyperLogLogService;

    @PostMapping("/pfadd")
    public ApiResponse<Boolean> pfadd(@Validated @RequestBody PFAddDTO record) {
        Boolean result = hyperLogLogService.pfadd(record);
        return ApiResponse.success(result);
    }

    @GetMapping("/pfcount/{key}")
    public ApiResponse<Long> pfcount(@PathVariable(value = "key") @NotNull String key) {
        Long result = hyperLogLogService.pfcount(key);
        return ApiResponse.success(result);
    }

    @PostMapping("/pfmerge")
    public ApiResponse<Boolean> pfmerge(@Validated @RequestBody PFMergeDTO record) {
        Boolean result = hyperLogLogService.pfmerge(record);
        return ApiResponse.success(result);
    }

}
