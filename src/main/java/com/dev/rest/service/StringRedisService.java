package com.dev.rest.service;

import com.dev.rest.dto.*;

public interface StringRedisService {

    String get(String key);

    void set(StringSetDTO record);

    Boolean incr(IncrDTO record);

    Boolean decr(DecrDTO record);

    Long len(String key);

    Boolean append(AppendDTO record);

    Boolean delete(String key);

    Boolean expire(ExpireDTO record);

    Long multiplication(MultiplicationDTO record);
}
