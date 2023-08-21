package com.dev.rest.utils.excel.writer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 大数据导出处理
 * File Name: DataHandler.java
 */
public interface DataHandler {

    /**
     * 判断是否还有数据待处理
     *
     * @return boolean true: 是, false: 否
     */
    boolean more();

    /**
     * 获取标题
     *
     * @return LinkedHashMap<String, String> 列标题组成的集合（格式：LinkedHashMap<Property, Name>）
     */
    LinkedHashMap<String, String> getTitles();

    /**
     * 获取数据
     * <P>
     * 本方法可以重复调用, 直到无法获取数据为止, 这种调用方式一般用于数据库访问读取数据
     * 也可以和<b>more</b>组合调用, 根据more方法的返回值判断是否还可以获取更多数据
     * </p>
     *
     * @return List<Map < String, Object>> 行数据组成的列表
     */
    List<Map<String, Object>> getData();

}
