package com.dev.rest.mapper;

import com.dev.rest.entity.Black;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cache.impl.PerpetualCache;
import java.util.List;

//注解方式开启mybatis二级缓存
//@CacheNamespace(implementation = PerpetualCache.class)
public interface BlackMapper {

    int exist(String mobile);

    List<String> selectAll();

    int insert(Black record);

    /**
     * for each insert
     * @param blackList
     * @return
     */
    int batchInsert(@Param("blackList") List<Black> blackList);

    Black selectByMobile(@Param("mobile") String mobile);

    int delete(String mobile);

    List<Black> selectList(Black record);
}
