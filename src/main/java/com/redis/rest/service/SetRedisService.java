package com.redis.rest.service;

import com.redis.rest.dto.SMoveDTO;
import com.redis.rest.dto.SetSAddDTO;

import java.util.Set;

public interface SetRedisService {
    Boolean sadd(SetSAddDTO record);

    Boolean sismember(String key, String member);

    Object spop(String key);

    Object srandmember(String key, Long count);

    Long srem(String key, String member);

    Boolean smove(SMoveDTO record);

    Set members(String key);

    Long size(String key);

    Set difference(String key,String otherKey);

    Boolean differenceAndStore(String key, String otherKey, String destKey);

    Set intersect(String key, String otherKey);

    Boolean intersectAndStore(String key, String otherKey, String destKey);

    Set union(String key, String otherKey);

    Boolean unionAndStore(String key, String otherKey, String destKey);
}
