package com.redis.rest.controller.apply;

import com.redis.rest.dto.AddBlackDTO;
import com.redis.rest.response.ApiResponse;
import com.redis.rest.service.BlackService;
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
 * 黑名单 set
 * @author: yaodong zhang
 * @create 2023/1/3
 */
@RestController
@RequestMapping("/apply/black")
public class BlackController {


    @Autowired
    private BlackService blackService;

    /**
     * 加入黑名单
     * @param record
     * @return
     */
    @PostMapping("")
    public ApiResponse<Boolean> addBlack(@Validated @RequestBody AddBlackDTO record) {
        Boolean result = blackService.addBlack(record);
        return ApiResponse.success(result);
    }

    /**
     * 是否黑名单用户
     * @param mobile
     * @return
     */
    @GetMapping("/{mobile}")
    public ApiResponse<Boolean> isBlack(@PathVariable(value = "mobile") @NotNull String mobile) {
        Boolean result = blackService.isBlack(mobile);
        return ApiResponse.success(result);
    }

    /**
     * 移出黑名单
     * @param mobile
     * @return
     */
    @DeleteMapping("/{mobile}")
    public ApiResponse<Boolean> removeBlack(@PathVariable(value = "mobile") @NotNull String mobile) {
        Boolean result = blackService.removeBlack(mobile);
        return ApiResponse.success(result);
    }

}
