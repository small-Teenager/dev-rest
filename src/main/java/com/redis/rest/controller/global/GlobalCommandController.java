package com.redis.rest.controller.global;

import com.redis.rest.response.ApiResponse;
import com.redis.rest.service.GlobalCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 全局命令
 * @author: yaodong zhang
 * @create 2022/12/27
 */
@RestController
@RequestMapping("/global/command")
public class GlobalCommandController {

    @Autowired
    private GlobalCommandService globalCommandService;

    @DeleteMapping("/{key}")
    public ApiResponse<Boolean> delete(@PathVariable(value = "key") @NotNull String key) {
        Boolean result = globalCommandService.delete(key);
        return ApiResponse.success(result);
    }

    @DeleteMapping("/expire/{key}/{timeout}")
    public ApiResponse<Boolean> expire(@PathVariable(value = "key") @NotNull String key,@PathVariable(value = "timeout") @NotNull Long timeout) {
        Boolean result = globalCommandService.expire(key,timeout);
        return ApiResponse.success(result);
    }

    @GetMapping("/exist/{key}")
    public ApiResponse<Boolean> exist(@PathVariable(value = "key") @NotNull String key) {
        Boolean result = globalCommandService.exist(key);
        return ApiResponse.success(result);
    }

    @GetMapping("/type/{key}")
    public ApiResponse<DataType> type(@PathVariable(value = "key") @NotNull String key) {
        DataType result = globalCommandService.type(key);
        return ApiResponse.success(result);
    }



}
