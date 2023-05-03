package com.redis.rest.utils;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * Redisson 布隆过滤器
 *
 * @author: yaodong zhang
 * @create 2023/1/30
 */
public class RedissonBloomFilterMain {


    public static void main(String args[]) {
        redissonBloomFilter();
    }

    private static void redissonBloomFilter() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        config.useSingleServer();
        //构造Redisson
        RedissonClient redisson = Redisson.create(config);

        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("bloomFilter");
        //初始化布隆过滤器：预计元素为100000000L,误差率为3%
        bloomFilter.tryInit(100000000L, 0.03);
        //将号码10086插入到布隆过滤器中
        bloomFilter.add("10086");
        //判断下面号码是否在布隆过滤器中
        System.err.println(bloomFilter.contains("123456"));//false
        System.err.println(bloomFilter.contains("10086"));//true
    }
}
