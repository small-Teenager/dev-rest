package com.dev.rest.mapper;

import com.dev.rest.entity.UserInfo;


public interface UserInfoMapper {

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);

}