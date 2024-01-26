package com.dev.rest.controller.apply;

import com.alibaba.fastjson.JSONObject;
import com.dev.rest.config.JwtConfig;
import com.dev.rest.annotation.RedisLimit;
import com.dev.rest.dto.UserLoginDTO;
import com.dev.rest.response.ApiResponse;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/apply/user")
public class UserController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JwtConfig jwtConfig ;

    @PostMapping("/smsCode")
    @RedisLimit(prefix = "user:login:smsCode", count = 1)
    public ApiResponse<String> smsCode(@RequestParam("mobile") String mobile){
        // 线程安全
       String result = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
       stringRedisTemplate.opsForValue().set("user:login:smsCode:"+mobile,result,6, TimeUnit.MINUTES);
       return ApiResponse.success(result);
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestParam("mobile") String mobile,
                             @RequestParam("code") String code){

        String oldCode = stringRedisTemplate.opsForValue().get("user:login:smsCode:"+mobile);
        if(StringUtils.isEmpty(oldCode)){
            throw new RuntimeException("验证码已失效");
        }
        if(!code.equals(oldCode)){
            throw new RuntimeException("验证码输入有误，请重新输入");
        }
        String token = jwtConfig.createToken(mobile);
        JSONObject json = new JSONObject();
        if (!StringUtils.isEmpty(token)) {
            json.put("token",token) ;
        }
        return ApiResponse.success(json) ;
    }

    @GetMapping("/code/{mobile}")
    public ApiResponse code(@PathVariable String mobile){
        String code = stringRedisTemplate.opsForValue().get("user:login:smsCode:"+mobile);
        return ApiResponse.success(code) ;
    }

    @PostMapping("/login2")
    public ApiResponse login2(@Validated @RequestBody UserLoginDTO userLogin){
        return ApiResponse.success(true) ;
    }
}
