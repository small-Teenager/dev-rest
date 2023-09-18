package com.dev.rest.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
//@CacheNamespace(implementation =)//注解方式开启二级缓存
public interface BlackMapper {
    @Insert("INSERT INTO `black`(mobile) VALUES(#{mobile});")
    int insert(String mobile);

    @Delete("delete from black where mobile = #{mobile}")
    int delete(String mobile);

    @Select("SELECT count(1) FROM black WHERE mobile = #{mobile}")
    int exist(String mobile);

    @Select("SELECT mobile FROM black")
    List<String> selectAll();
}
