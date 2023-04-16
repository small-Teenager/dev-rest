package com.redis.rest.service;

public interface LocalCacheService {

    String localCache(String id);

    String redisCache(String id);

}
