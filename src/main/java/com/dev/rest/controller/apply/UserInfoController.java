package com.dev.rest.controller.apply;

import com.alibaba.fastjson.JSON;
import com.dev.rest.entity.UserInfo;
import com.dev.rest.response.ApiResponse;
import com.dev.rest.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyaodong
 * @version 1.0
 * @description
 * @create 2023/11/27 20:00
 */
@RestController
@RequestMapping("/apply/user-info")
public class UserInfoController {


    @Autowired
    private UserInfoService userInfoService;


    /**
     * 数据解密+数据脱敏
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ApiResponse<UserInfo> detail(@PathVariable Long id) {
        UserInfo userInfo = userInfoService.selectById(id);
        System.err.println(JSON.toJSONString(userInfo));
        return ApiResponse.success(userInfo);
    }
}
