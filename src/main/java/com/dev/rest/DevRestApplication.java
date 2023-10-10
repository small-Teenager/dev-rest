package com.dev.rest;

import com.dev.rest.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
@EnableCaching
public class DevRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevRestApplication.class, args);
    }

    @Bean
    @Lazy
    public IdWorker idWorker() {
        return new IdWorker();
    }

}
