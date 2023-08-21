package com.dev.rest.controller.apply;

import com.dev.rest.response.ApiResponse;
import com.dev.rest.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 签到 bitmap 按自然月签到
 *
 * @author: yaodong zhang
 * @create 2022/12/27
 */
@RestController
@RequestMapping("/apply/sign")
public class SignController {

    @Autowired
    private SignService signService;


    /**
     * 签到
     *
     * @return
     */
    @PostMapping("")
    public ApiResponse<Boolean> signIn() {
        //用户Uid
        String userId = "1234";
        Boolean result = signService.signIn(userId);
        return ApiResponse.success(result);
    }


    /**
     * 用户当天的签到状态 可以使用getbit指令。
     *
     * @return
     */
    @GetMapping("")
    public ApiResponse<Boolean> signStatus() {
        //用户Uid
        String userId = "1234";
        Boolean result = signService.signStatus(userId);
        return ApiResponse.success(result);
    }

    /**
     * 用户当月的签到次数
     *
     * @return
     */
    @GetMapping("/month-count")
    public ApiResponse<Long> monthCount() {
        //用户Uid
        String userId = "1234";
        Long result = signService.monthCount(userId);
        return ApiResponse.success(result);
    }

    /**
     * 用户当月连续签到次数
     *
     * @return
     */
    @GetMapping("/con-month-count")
    public ApiResponse<Long> conMonthCount() {
        //用户Uid
        String userId = "1234";
        Long result = signService.conMonthCount(userId);
        return ApiResponse.success(result);
    }

    /**
     * 获取用户当月签到日历
     * @return Map<String, Boolean> key:yyyy-MM-dd (2022-12-13)
     */
    @GetMapping("/sign-calendar")
    public ApiResponse<Map<String, Boolean>> signCalendar() {
        //用户Uid
        String userId = "1234";
        Map<String, Boolean> result = signService.signCalendar(userId);
        return ApiResponse.success(result);
    }

    /**
     * 指定日期补签
     *
     * @param signDate yyyy-MM-dd
     * @return
     */
    @PostMapping("/countersign/{signDate}")
    public ApiResponse<Boolean> countersign(@PathVariable(value = "signDate") @NotNull String signDate) {
        String userId = "1234";
        Boolean result = signService.countersign(userId, signDate);
        return ApiResponse.success(result);
    }

}

