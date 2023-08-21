package com.dev.rest.controller.apply;

import com.dev.rest.service.LocalCacheService;
import com.dev.rest.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/apply/local-cache")
public class LocalCacheController {

    @Autowired
    private LocalCacheService localCacheService;

    @GetMapping("/{id}")
   public ApiResponse<String> localCache(@PathVariable(value = "id") @NotNull String id) {
        return ApiResponse.success(localCacheService.localCache(id));
    }


    @GetMapping("/redis/{id}")
    public ApiResponse<String> redisCache(@PathVariable(value = "id") @NotNull String id) {
        return ApiResponse.success(localCacheService.redisCache(id));
    }

}
