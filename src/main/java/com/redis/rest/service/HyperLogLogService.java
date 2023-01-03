package com.redis.rest.service;

import com.redis.rest.dto.PFAddDTO;
import com.redis.rest.dto.PFMergeDTO;

public interface HyperLogLogService {
    Boolean pfadd(PFAddDTO record);

    Long pfcount(String key);

    Boolean pfmerge(PFMergeDTO record);
}
