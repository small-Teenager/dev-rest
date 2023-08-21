package com.dev.rest.controller.apply;

import com.dev.rest.response.ApiResponse;
import com.dev.rest.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 计数器
 * @author: yaodong zhang
 * @create 2023/1/7
 */
@RestController
@RequestMapping("/apply/counter")
public class CounterController {

    @Autowired
    private CounterService counterService;

    /**
     * 粉丝数+1
     *
     * @param userId
     * @return
     */
    @PostMapping("/fan/{userId}")
    public ApiResponse<Boolean> fanIncr(@PathVariable(value = "userId") @NotNull String userId) {
        Boolean result = counterService.fanIncr(userId);
        return ApiResponse.success(result);
    }

    /**
     * 粉丝数-1
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/fan/{userId}")
    public ApiResponse<Boolean> fanDecr(@PathVariable(value = "userId") @NotNull String userId) {
        Boolean result = counterService.fanDecr(userId);
        return ApiResponse.success(result);
    }

    /**
     * 关注数+1
     *
     * @param userId
     * @return
     */
    @PostMapping("/concern/{userId}")
    public ApiResponse<Boolean> concernIncr(@PathVariable(value = "userId") @NotNull String userId) {
        Boolean result = counterService.concernIncr(userId);
        return ApiResponse.success(result);
    }

    /**
     * 关注数-1
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/concern/{userId}")
    public ApiResponse<Boolean> concernDecr(@PathVariable(value = "userId") @NotNull String userId) {
        Boolean result = counterService.concernDecr(userId);
        return ApiResponse.success(result);
    }

    /**
     * 获赞数+1
     *
     * @param userId
     * @return
     */
    @PostMapping("/liked/{userId}")
    public ApiResponse<Boolean> likedIncr(@PathVariable(value = "userId") @NotNull String userId) {
        Boolean result = counterService.likedIncr(userId);
        return ApiResponse.success(result);
    }

    /**
     * 获取 获赞数 关注数 粉丝数
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ApiResponse<Map> counterMap(@PathVariable(value = "userId") @NotNull String userId) {
        Map result = counterService.counterMap(userId);
        return ApiResponse.success(result);
    }

}
