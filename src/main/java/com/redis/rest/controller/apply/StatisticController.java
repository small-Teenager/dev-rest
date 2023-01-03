package com.redis.rest.controller.apply;

import com.redis.rest.response.ApiResponse;
import com.redis.rest.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
