package com.redis.rest.service;

import org.springframework.data.redis.connection.DataType;

public interface GlobalCommandService {
    Boolean delete(String key);

    Boolean expire(String key,Long timeout);

    Boolean exist(String key);

    DataType type(String key);
}
