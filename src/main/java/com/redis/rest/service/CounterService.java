package com.redis.rest.service;

import java.util.Map;

public interface CounterService {
    Boolean fanIncr(String userId);

    Boolean concernIncr(String userId);

    Boolean likedIncr(String userId);

    Map counterMap(String userId);

    Boolean fanDecr(String userId);

    Boolean concernDecr(String userId);
}
