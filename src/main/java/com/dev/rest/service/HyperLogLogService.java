package com.dev.rest.service;

import com.dev.rest.dto.PFAddDTO;
import com.dev.rest.dto.PFMergeDTO;

public interface HyperLogLogService {
    Boolean pfadd(PFAddDTO record);

    Long pfcount(String key);

    Boolean pfmerge(PFMergeDTO record);
}
