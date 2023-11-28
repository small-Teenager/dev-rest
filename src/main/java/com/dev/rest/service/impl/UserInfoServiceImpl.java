package com.dev.rest.service.impl;


import com.dev.rest.entity.UserInfo;
import com.dev.rest.mapper.UserInfoMapper;
import com.dev.rest.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo selectById(Long id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }
}
