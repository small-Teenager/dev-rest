package com.redis.rest.service;

import com.redis.rest.dto.AppendDTO;
import com.redis.rest.dto.DecrDTO;
import com.redis.rest.dto.ExpireDTO;
import com.redis.rest.dto.IncrDTO;
import com.redis.rest.dto.StringSetDTO;

public interface StringRedisService {

    String get(String key);

    void set(StringSetDTO record);

    Boolean incr(IncrDTO record);

    Boolean decr(DecrDTO record);

    Long len(String key);

    Boolean append(AppendDTO record);

    Boolean delete(String key);

    Boolean expire(ExpireDTO record);
}
