package com.dev.rest.service;

import com.dev.rest.dto.AddBlackDTO;
import com.dev.rest.entity.Black;
import com.github.pagehelper.PageInfo;


import java.util.List;

/**
 * @author: yaodong zhang
 * @create 2023/1/3
 */
public interface BlackService {
    Boolean addBlack(AddBlackDTO record);

    Boolean isBlack(String mobile);

    Boolean removeBlack(String mobile);

    Boolean init();

    List<String> selectAll();

    Black selectByMobile(String mobile);

    PageInfo<Black> selectPage(Black record, int page,int size);

    /**
     * for each  batchInsert
     * @param blackList
     * @return
     */
    int batchInsert(List<Black> blackList);
}
