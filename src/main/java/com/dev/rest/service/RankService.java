package com.dev.rest.service;

import com.dev.rest.dto.AddScoreDTO;

import java.util.Set;

public interface RankService {
    Double addScore(AddScoreDTO record);

    Set reverseRangeWithScores(Long page,Long size);
}
