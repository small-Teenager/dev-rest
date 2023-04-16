package com.redis.rest.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Configuration
public class LocalCacheConfig {
    @Bean("caffeineCacheManager")
    @Primary
    public CacheManager cacheManager(){
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        ArrayList<Cache> caches = new ArrayList<>();
        caches.add(new CaffeineCache("local-cache",
                Caffeine.newBuilder()
                        .expireAfterWrite(100, TimeUnit.SECONDS)
                        .recordStats()
                        .maximumSize(Integer.MAX_VALUE)
                        .removalListener((key, value, cause) -> {
                        }).build()));
        simpleCacheManager.setCaches(caches);
        return simpleCacheManager;
    }


    @Bean("redisCacheManager")
    public CacheManager redisCacheManager(RedisConnectionFactory factory){
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 设置缓存的默认过期时间
                .entryTtl(Duration.ofSeconds(180))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()))
                // 不缓存空值
                .disableCachingNullValues();

        return RedisCacheManager
                .builder(factory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }
}