package com.redis.rest.controller.basic;

import com.redis.rest.dto.LPushDTO;
import com.redis.rest.dto.LRemDTO;
import com.redis.rest.dto.RpushDTO;
import com.redis.rest.response.ApiResponse;
import com.redis.rest.service.ListRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 内部编码
 * ziplist(压缩列表)：
 * linkedlist(链表)：
 * @author: yaodong zhang
 * @create 2022/12/19
 */
@RestController
@RequestMapping("/basic/list")
public class ListController {

    @Autowired
    private ListRedisService listRedisService;


    @PostMapping("/lpush")
    public ApiResponse<Boolean> lPush(@Validated @RequestBody LPushDTO record) {
        Boolean result = listRedisService.lPush(record);
        return ApiResponse.success(result);
    }

    @PostMapping("/lpop/{key}")
    public ApiResponse<String> lPop(@PathVariable(value = "key") @NotNull String key) {
        String result = listRedisService.lpop(key);
        return ApiResponse.success(result);
    }

    @PostMapping("/rpush")
    public ApiResponse<Boolean> rpush(@Validated @RequestBody RpushDTO record) {
        return ApiResponse.success(listRedisService.rpush(record));
    }

    @PostMapping("/rpop/{key}")
    public ApiResponse<String> rPop(@PathVariable(value = "key") @NotNull String key) {
        String result = listRedisService.rPop(key);
        return ApiResponse.success(result);
    }

    @PostMapping("/lrem")
    public ApiResponse<Boolean> lRem(@Validated @RequestBody LRemDTO record) {
        return ApiResponse.success(listRedisService.lRem(record));
    }

    @GetMapping("/llen/{key}")
    public ApiResponse<Long> lLen(@PathVariable(value = "key") @NotNull String key) {
        Long result = listRedisService.lLen(key);
        return ApiResponse.success(result);
    }

    @GetMapping("/lindex/{key}/{index}")
    public ApiResponse<String> lindex(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "index") @NotNull Long index) {
        String result = listRedisService.lindex(key, index);
        return ApiResponse.success(result);
    }

    @PostMapping("/ltrim/{key}/{start}/{end}")
    public void ltrim(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "start") @NotNull Long start, @PathVariable(value = "start") @NotNull Long end) {
        listRedisService.ltrim(key, start, end);
    }

    @PutMapping("/lset/{key}/{index}/{value}")
    public void lset(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "index") @NotNull Long index,@PathVariable(value = "value") @NotNull String value) {
        listRedisService.lset(key, index,value);
    }

}
