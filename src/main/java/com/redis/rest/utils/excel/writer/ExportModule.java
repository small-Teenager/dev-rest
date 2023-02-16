package com.redis.rest.utils.excel.writer;

/**
 * Description: Excel数据模板接口
 * File Name: ExportModule.java
 */
public interface ExportModule extends Module {

    public final static int FORMAT_XLS = 1;
    public final static int FORMAT_XLSX = 1 >> 1;

    /**
     * 解析模板文件
     */
    void parse();

    /**
     * 将模板转换为HTML表格Css样式（部分模板色调上可能有偏差）
     *
     * @return String css字符串
     */
    String transformToCss();

}
