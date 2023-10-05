package com.dev.rest.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yaodong zhang
 * @create 2023/1/11
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String ip;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient(){
        // 此为单机模式
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        // 当然，这儿有很多模式可选择，主从、集群、复制、哨兵 等等 ... ...
        // config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword(password);
        config.useSingleServer()
                .setAddress("redis://" + ip + ":" + port)
                .setDatabase(database)
                .setPassword(password);
        config.setLockWatchdogTimeout(10000L);
        return Redisson.create(config);
    }
}
