package com.redis.rest.utils.excel.reader;

import java.util.List;
import java.util.Map;

/**
 * Description: Excel数据读取工具类接口
 * File Name: ExcelReader.java
 */
public interface ExcelReader {

    /**
     * 是否还有数据待读取
     *
     * @return true: 是, false：否.
     */
    boolean hasMore();

    /**
     * 读取并解析返回数据列表
     * <p>
     * 返回的数据按列顺序排列
     * </p>
     *
     * @return ist<Object> 数据流
     */
    List<Object> read();

    /**
     * 读取指定行数并解析返回数据列表
     * <p>
     * 返回的数据按列顺序排列
     * </p>
     *
     * @param length 读取的行数, 如超出剩余的行数, 则返回剩余的行数据, 如已经是最后一行, 返回Null
     * @return List<List < Object>> 数据流
     */
    List<List<Object>> read(int length);

    /**
     * 读取并解析返回数据列表
     *
     * @param fields 列名按顺序排列的列表, 返回的Map数据以field属性作为Key值
     * @return Map<String, Object> 数据流
     */
    Map<String, Object> read(List<String> fields);

    /**
     * 读取指定行数并解析返回数据列表
     *
     * @param fields 列名按顺序排列的列表, 返回的Map数据以field属性作为Key值
     * @param length 读取的行数, 如超出剩余的行数, 则返回剩余的行数据, 如已经是最后一行, 返回Null
     * @return List<Map < String, Object>> 数据流
     */
    List<Map<String, Object>> read(List<String> fields, int length);

    /**
     * 获取当前行号（从 0 开始）
     *
     * @return int
     */
    int getMark();

    /**
     * 关闭文件流
     */
    void close();

}
