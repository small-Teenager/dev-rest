package com.dev.rest.service.impl;

import com.dev.rest.dto.AddScoreDTO;
import com.dev.rest.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author: yaodong zhang
 * @create 2022/12/30
 */
@Service
public class RankServiceImpl implements RankService {

    @Autowired
    private RedisTemplate redisTemplate;

    private static String RANK_KRY = "rank";

    @Override
    public Double addScore(AddScoreDTO record) {
        return redisTemplate.opsForZSet().incrementScore(RANK_KRY, record.getValue(), record.getScore());
    }

    @Override
    public Set reverseRangeWithScores(Long page, Long size) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(RANK_KRY, (page -1)* size, page*size-1);
    }
}
