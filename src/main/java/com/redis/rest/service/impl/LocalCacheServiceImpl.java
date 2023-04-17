package com.redis.rest.service.impl;

import com.redis.rest.service.LocalCacheService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LocalCacheServiceImpl implements LocalCacheService {

    @Override
    @Cacheable(value = "local-cache", key = "#id", cacheManager = "caffeineCacheManager")
    public String localCache(String id) {
        System.err.println("进入local-cache查询缓存");
        return "local-cache缓存";
    }

    @Override
    @Cacheable(value = "redis:cache", key = "#id", cacheManager = "redisCacheManager", sync = true)
    public String redisCache(String id) {
        System.err.println("进入redis-cache查询缓存");
        return "redis-cache缓存";
    }
}
