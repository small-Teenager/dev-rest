package com.redis.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class RedisRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisRestApplication.class, args);
    }

}
