package com.dev.rest.utils.excel.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: list数据导出模板接口（专用于导出List类型数据）
 * File Name: ListDataModule.java
 */
public interface ListDataModule extends ExportModule {

    /**
     * 读取指定行的数据
     *
     * @param i 行号（从0开始）
     * @return List<String> 首行单元格文本组成的列表, 如单元格为空也必须放入空元素
     * @throws IOException
     */
    List<String> read(int i) throws IOException;

    /**
     * 按模板设定导出List数据到指定输出流
     *
     * @param titles 表头标题, 标题全为空时隐藏表头; 格式：Map<字段名: 表头标题>
     * @param data   导出数据; 格式：List<Map<字段名: 数据对象>>
     * @param stream 输出流
     */
    void export(LinkedHashMap<String, String> titles, List<Map<String, ?>> data, OutputStream stream) throws IOException;

}
