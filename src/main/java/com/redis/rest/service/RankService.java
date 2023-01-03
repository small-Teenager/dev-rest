package com.redis.rest.service;

import com.redis.rest.dto.AddScoreDTO;

import java.util.Set;

public interface RankService {
    Double addScore(AddScoreDTO record);

    Set reverseRangeWithScores(Long page,Long size);
}
