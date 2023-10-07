package com.dev.rest.redisson;

import com.dev.rest.DevRestApplicationTests;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

public class RedissonTest extends DevRestApplicationTests {

    @Autowired
    private RedissonClient redissonClient;


    @Test
    public void bloomFilterTest() {

        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("bloomFilter");
        //初始化布隆过滤器：预计元素为100000000L,误差率为3%
        bloomFilter.tryInit(100000000L, 0.03);
        //将号码10086插入到布隆过滤器中
        bloomFilter.add("10086");
        //判断下面号码是否在布隆过滤器中
        System.err.println(bloomFilter.contains("123456"));//false
        System.err.println(bloomFilter.contains("10086"));//true
    }
}
