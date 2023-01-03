package com.redis.rest.service;

import com.redis.rest.dto.LPushDTO;
import com.redis.rest.dto.LRemDTO;
import com.redis.rest.dto.RpushDTO;

public interface ListRedisService {
    Boolean lPush(LPushDTO record);

    String lpop(String key);

    Boolean rpush(RpushDTO record);

    String rPop(String key);

    Boolean lRem(LRemDTO record);

    Long lLen(String key);

    String lindex(String key,Long index);

    void ltrim(String key,long start,long end);

    void lset(String key, Long index, String value);
}
