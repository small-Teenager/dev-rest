package com.redis.rest.service;

import com.redis.rest.dto.HSetDTO;

import java.util.Map;

/**
 * @author: yaodong zhang
 * @create 2022/12/28
 */
public interface HashService {
    void hset(HSetDTO record);

    String hget(String key, String hashKey);

    Map entries(String key);

    Boolean hdel(String key, String hashKey);

    Long hlen(String key);

    Boolean hexists(String key, String hashKey);
}
