package com.dev.rest.mapper;

import com.dev.rest.entity.Black;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
//@CacheNamespace(implementation =)//注解方式开启二级缓存
public interface BlackMapper {
    @Insert("INSERT INTO `t_black`(id,mobile) VALUES(#{id},#{mobile});")
    int insert(Black mobile);

    @Delete("update  t_black set deleted=1 where mobile = #{mobile} and deleted=0")
    int delete(String mobile);

    @Select("SELECT count(1) FROM t_black WHERE mobile = #{mobile} and deleted=0")
    int exist(String mobile);

    @Select("SELECT mobile FROM t_black where deleted=0")
    List<String> selectAll();

    @Select("SELECT * FROM t_black WHERE mobile = #{mobile} and deleted=0")
    Black selectByMobile(String mobile);
}
