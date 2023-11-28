package com.dev.rest.service;

import com.dev.rest.entity.UserInfo;

public interface UserInfoService {

    UserInfo selectById(Long id);
}
