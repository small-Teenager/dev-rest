package com.dev.rest.utils.excel.writer;

import com.dev.rest.exception.UnsupportedExcelDataException;
import com.dev.rest.utils.excel.writer.module.PoiELModule;
import com.dev.rest.utils.excel.writer.module.PoiListDataModule;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: Excel数据导出工具
 * File Name: ExcelWriter.java
 */
public class ExcelWriter {

    private final static Logger log = LoggerFactory.getLogger(ExcelWriter.class);

    public static final int EXCEL_FORMAT_XLS = 1;
    public static final int EXCEL_FORMAT_XLSX = 1 << 1;

    public static final int EXCEL_XLS_ROW_COUNT_MAX = 65535; // xls最大输出行数
    public static final int EXCEL_XLS_COLUMN_COUNT_MAX = 256; // xls最大输出列数

    public static final int EXCEL_XLSX_ROW_COUNT_MAX = 65535; // xlsx最大输出行数
    public static final int EXCEL_XLSX_COLUMN_COUNT_MAX = 16384; // xlsx最大输出列数

    /**
     * 按指定的模板导出List数据集, 包括单行表头, 只能输出单个Sheet, 不包含合并单元格, 可以有图片、计算公式
     * <P>
     * 适合于小数据集的导出（Rowcount <= 65535）
     * </p>
     *
     * @param titles     表格标题栏
     * @param data       数据集
     * @param moduleName 导出模板
     * @param format     导出格式（1: xls[1997-2003]; 2: xlsx[2007+]）
     * @param stream     输出流
     */
    public static void write(LinkedHashMap<String, String> titles, List<Map<String, ?>> data, String moduleName, int format, OutputStream stream) throws UnsupportedExcelDataException, FileNotFoundException, IOException {
        if (titles == null || titles.size() == 0 || data == null || StringUtils.isBlank(moduleName) || stream == null) {
            throw new UnsupportedExcelDataException(UnsupportedExcelDataException.ILLEGAL_PARAMS);
        }
        if ((format == EXCEL_FORMAT_XLS && titles.size() > EXCEL_XLS_COLUMN_COUNT_MAX) || (format == EXCEL_FORMAT_XLSX && titles.size() > EXCEL_XLSX_COLUMN_COUNT_MAX)) {
            throw new UnsupportedExcelDataException(UnsupportedExcelDataException.EXCEED_COLUMN_COUNT_LIMIT_ERROR);
        }
        if ((format == EXCEL_FORMAT_XLS && data.size() > EXCEL_XLS_ROW_COUNT_MAX) || (format == EXCEL_FORMAT_XLSX && data.size() > EXCEL_XLSX_ROW_COUNT_MAX)) {
            throw new UnsupportedExcelDataException(UnsupportedExcelDataException.EXCEED_ROW_COUNT_LIMIT_ERROR);
        }
        ListDataModule mod = getListDataModule(moduleName, format);
        mod.export(titles, data, stream);
        log.info("Excel数据导出完成");
    }

    /**
     * 按指定的Xls模板导出不规则数据集, 可以输出多个Sheet, 可以包含合并单元格、图片、计算公式
     * <p>
     * bean通常是一个统计Bean对象, 私有属性对应导出模板中的每一个输出项
     * bean对应的模板是以Bean类名命名, 有分别对应xls和xlsx格式的模板
     * </p>
     *
     * @param bean   封装各项数据的Bean对象
     * @param format 导出格式（1: xls[1997-2003]; 2: xlsx[2007+]）
     * @param stream 输出流
     */
    public static void write(Serializable bean, String moduleName, int format, OutputStream stream) throws UnsupportedExcelDataException, FileNotFoundException, IOException {
        if (bean == null || StringUtils.isBlank(moduleName) || stream == null) {
            throw new UnsupportedExcelDataException(UnsupportedExcelDataException.ILLEGAL_PARAMS);
        }
        ELModule mod = getELModule(moduleName, format);
        mod.export(bean, format, stream);
        log.info("Excel数据导出完成");
    }

    /**
     * 导出大数据记录（仅支持xlsx[2007+]）
     *
     * @param handler 数据处理方式
     * @param stream  输出流
     */
    public static void write(DataHandler handler, OutputStream stream) throws UnsupportedExcelDataException, FileNotFoundException, IOException {
        if (handler == null || handler.getTitles() == null || handler.getTitles().size() == 0 || stream == null) {
            throw new UnsupportedExcelDataException(UnsupportedExcelDataException.ILLEGAL_PARAMS);
        }
        BigDataXlsxExport.export(handler, stream);
        log.info("Excel数据导出完成");
    }

    /**
     * 获取列表模板对象
     *
     * @return ListDataModule
     */
    private static ListDataModule getListDataModule(String moduleName, int format) throws FileNotFoundException, IOException {
        return new PoiListDataModule(moduleName, format);
    }

    /**
     * 获取支持EL表达式的模板对象
     *
     * @return ELModule
     */
    private static ELModule getELModule(String moduleName, int format) throws FileNotFoundException, IOException {
        return new PoiELModule(moduleName, format);
    }

}
