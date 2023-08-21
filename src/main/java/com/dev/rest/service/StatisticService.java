package com.dev.rest.service;

/**
 * @author: yaodong zhang
 * @create 2022/12/30
 */
public interface StatisticService {

    Long dailyActiveUser();


    Long dailyActiveUser(String day);

    /**
     * 记录日活用户
     * @param userId 用户的主键id
     * @return
     */
    Boolean addDailyActive(Long userId);

    Boolean isActiveUser(String day, Long userId);
}
