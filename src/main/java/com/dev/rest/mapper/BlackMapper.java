package com.dev.rest.mapper;

import com.dev.rest.config.DataSource.DBTypeEnum;
import com.dev.rest.config.DataSource.DataSource;
import com.dev.rest.entity.Black;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cache.impl.PerpetualCache;
import java.util.List;

//注解方式开启mybatis二级缓存
//@CacheNamespace(implementation = PerpetualCache.class)
public interface BlackMapper {

    @DataSource(type = DBTypeEnum.SLAVE)
    int exist(String mobile);

    @DataSource(type = DBTypeEnum.SLAVE)
    List<String> selectAll();

    @DataSource(type = DBTypeEnum.MASTER)
    int insert(Black record);

    /**
     * for each insert
     * @param blackList
     * @return
     */
    @DataSource(type = DBTypeEnum.MASTER)
    int batchInsert(@Param("blackList") List<Black> blackList);

    Black selectByMobile(@Param("mobile") String mobile);

    int delete(String mobile);

    List<Black> selectList(Black record);
}
