package com.redis.rest.service;

/**
 * @author: yaodong zhang
 * @create 2022/12/30
 */
public interface StatisticService {

    Long dailyActiveUser();

    /**
     * 记录日活用户
     * @param id 用户的主键id
     * @return
     */
    Boolean addDailyActive(Long id);
}
