package com.redis.rest.controller.apply;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * pipeline 管道技术
 * @author: yaodong zhang
 * @create 2023/1/9
 */
@RestController
@RequestMapping("/apply/pipelined")
public class PipelinedController {
    private static final Logger log = LoggerFactory.getLogger(PipelinedController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping(value = "/add/pipeline")
    public void addPipeline() {
        long start = System.currentTimeMillis();
        redisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
//                for (int i = 0; i < 100; i++) {
//                    connection.set(("pipeline:" + i).getBytes(), String.valueOf(i).getBytes());
//                }
                Map<byte[],byte[]> tuple = new HashMap<>();
                for (int i = 0; i < 100; i++) {
                    tuple.put(("pipeline:" + i).getBytes(), String.valueOf(i).getBytes());
                }
                connection.mSet(tuple);
                return null;
            }
        });
        long end = System.currentTimeMillis();
        log.info("addPipeline 耗时：{}", end - start);
    }

    @GetMapping(value = "/add/single")
    public void addSingle() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            redisTemplate.opsForValue().set("single:" + i, String.valueOf(i));
        }
        if(log.isDebugEnabled()){
            long end = System.currentTimeMillis();
            log.info("addSingle 耗时：{}", end - start);
        }
    }

}
