package com.redis.rest.service;

import com.redis.rest.dto.AddBlackDTO;

/**
 * @author: yaodong zhang
 * @create 2023/1/3
 */
public interface BlackService {
    Boolean addBlack(AddBlackDTO record);

    Boolean isBlack(String mobile);

    Boolean removeBlack(String mobile);
}
