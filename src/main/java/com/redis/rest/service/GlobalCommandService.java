package com.redis.rest.service;

import org.springframework.data.redis.connection.DataType;

import java.util.Set;

public interface GlobalCommandService {
    Boolean delete(String key);

    Boolean expire(String key,Long timeout);

    Boolean exist(String key);

    DataType type(String key);

    Boolean rename(String key, String newKey);

    Set keys(String pattern);
}
