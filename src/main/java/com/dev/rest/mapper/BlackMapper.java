package com.dev.rest.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
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
