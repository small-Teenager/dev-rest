package com.redis.rest;

import com.redis.rest.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class RedisRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisRestApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }

}
