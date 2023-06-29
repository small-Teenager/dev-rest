package com.redis.rest.service.impl;

import com.redis.rest.service.LocalCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LocalCacheServiceImpl implements LocalCacheService {

    private static final Logger log = LoggerFactory.getLogger(LocalCacheService.class);
    @Override
    @Cacheable(value = "local-cache", key = "#id", cacheManager = "caffeineCacheManager")
    public String localCache(String id) {
        if(log.isDebugEnabled()){
            log.info("进入local-cache查询缓存");
        }
        return "local-cache缓存";
    }

    @Override
    @Cacheable(value = "redis:cache", key = "#id", cacheManager = "redisCacheManager", sync = true)
    public String redisCache(String id) {
        if(log.isDebugEnabled()){
            log.info("进入redis-cache查询缓存");
        }
        return "redis-cache缓存";
    }
}
