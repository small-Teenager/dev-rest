package com.dev.rest.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: yaodong zhang
 * @create 2023/1/11
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host:127.0.0.1}")
    private String ip;

    @Value("${spring.redis.port:6379}")
    private String port;

    @Value("${spring.redis.database:0}")
    private int database;

    @Value("${spring.redis.password:redis@com}")
    private String password;

    @Value("${spring.redis.cluster.nodes:127.0.0.1:7701,127.0.0.1:7702,127.0.0.1:7703}")
    private String clusterNodes;

    @Bean
    public RedissonClient redissonClient() {
        // 此为单机模式
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        // 当然，这儿有很多模式可选择，主从、集群、复制、哨兵 等等 ... ...
        // config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword(password);
//        config.useSingleServer()
//                .setAddress("redis://" + ip + ":" + port)
//                .setDatabase(database)
//                .setPassword(password);
//        config.setLockWatchdogTimeout(10000L);

        //集群
        String[] clusterNodeArr = clusterNodes.split(",");
        config.useClusterServers()
                .setScanInterval(2000);
//                .setNodeAddresses(nodeAddressList);
        for (String cluster : clusterNodeArr) {
            config.useClusterServers().addNodeAddress("redis://" + cluster);
        }
        return Redisson.create(config);
    }
}
