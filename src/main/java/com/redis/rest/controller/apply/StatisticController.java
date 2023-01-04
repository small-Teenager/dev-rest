package com.redis.rest.controller.apply;

import com.redis.rest.response.ApiResponse;
import com.redis.rest.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author: yaodong zhang bitmap
 * @create 2022/12/30
 */
@RestController
@RequestMapping("/apply/statistic")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;


    /**
     * 当天活跃用户数
     * Daily Active User
     * @return
     */
    @GetMapping("/dau")
    public ApiResponse<Long> dailyActiveUser() {
        //用户Uid
        Long result = statisticService.dailyActiveUser();
        return ApiResponse.success(result);
    }

    /**
     * 指定天数活跃用户数
     * Daily Active User
     * @param day yyyyMMdd
     * @return
     */
    @GetMapping("/dau/{day}")
    public ApiResponse<Long> dailyActiveUser(@PathVariable(value = "day") @NotNull String day) {
        //用户Uid
        Long result = statisticService.dailyActiveUser(day);
        return ApiResponse.success(result);
    }

    /**
     * 指定天数用户是否活跃
     * @param day yyyyMMdd
     * @return
     */
    @GetMapping("/{day}/{userId}")
    public ApiResponse<Boolean> isActiveUser(@PathVariable(value = "day") @NotNull String day,@PathVariable(value = "userId") @NotNull Long userId) {
        //用户Uid
        Boolean result = statisticService.isActiveUser(day,userId);
        return ApiResponse.success(result);
    }

    /**
     * 记录日活用户
     * @param userId
     * @return
     */
    @PostMapping("/dau/{userId}")
    public ApiResponse<Boolean> addDailyActive(@PathVariable(value = "userId") @NotNull Long userId) {
        //用户Uid
        Boolean result = statisticService.addDailyActive(userId);
        return ApiResponse.success(result);
    }

}
