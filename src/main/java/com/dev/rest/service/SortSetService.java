package com.dev.rest.service;

import com.dev.rest.dto.IncrementScoreDTO;
import com.dev.rest.dto.SortSetZAddDTO;

import java.util.Set;

public interface SortSetService {
    Boolean zadd(SortSetZAddDTO record);

    Double incrementScore(IncrementScoreDTO record);

    Double score(String key, String object);

    Long size(String key);

    Long count(String key, Double min, Double max);

    Set range(String key, Long start, Long end);

    Set rangeByScore(String key, Double min, Double max);

    Long rank(String key, String object);

    Long reverseRank(String key, String object);

    Set reverseRange(String key, Long start, Long end);

    Boolean remove(String key, String object);

    Boolean removeRange(String key, Long start, Long end);

    Boolean removeRangeByScore(String key, Double min, Double max);
}
