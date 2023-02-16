package com.redis.rest.utils.excel.reader.impl;

import com.redis.rest.exception.FileErrorException;
import com.redis.rest.utils.excel.PoiUtils;
import com.redis.rest.utils.excel.reader.ExcelReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: Excel数据读取工具类接口POI实现
 * File Name: PoiExcelReader.java
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 */
public class PoiExcelReader implements ExcelReader {

    private final static Logger log = LoggerFactory.getLogger(PoiExcelReader.class);

    private Workbook workbook;

    private int mark = 0;      // 当前读取的位置（zero-based）
    private int maxLength = 0; // 最大行（zero-based）

    public PoiExcelReader(File file) throws FileErrorException, IOException {
        if (!(file.exists() && file.isFile())) {
            throw new FileErrorException(FileErrorException.FILE_NOT_FOUND);
        }
        String suffix = file.getName();
        if (!("xls".equalsIgnoreCase(suffix) || "xlsx".equalsIgnoreCase(suffix))) {
            throw new FileErrorException("Excel", FileErrorException.FILE_EXTENSION_ERROR);
        }
        FileInputStream fis = new FileInputStream(file);
        workbook = "xls".equalsIgnoreCase(suffix) ? new HSSFWorkbook(fis) : new XSSFWorkbook(fis);
        fis.close();
        if (workbook.getSheetAt(0) != null) {
            maxLength = workbook.getSheetAt(0).getLastRowNum();
        }
    }

    public PoiExcelReader(InputStream is, String suffix) throws FileErrorException, IOException {
        if (is == null) {
            throw new FileErrorException(FileErrorException.FILE_NOT_FOUND);
        }
        if (!("xls".equalsIgnoreCase(suffix) || "xlsx".equalsIgnoreCase(suffix))) {
            throw new FileErrorException("Excel", FileErrorException.FILE_EXTENSION_ERROR);
        }
        workbook = "xls".equalsIgnoreCase(suffix) ? new HSSFWorkbook(is) : new XSSFWorkbook(is);
        is.close();
        if (workbook.getSheetAt(0) != null) {
            maxLength = workbook.getSheetAt(0).getLastRowNum();
        }
    }

    @Override
    public boolean hasMore() {
        return mark <= maxLength;
    }

    @Override
    public List<Object> read() {
        List<List<Object>> list = read(1);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<List<Object>> read(int length) {
        if (length <= 0 || !hasMore()) {
            return null;  // 目前不支持重复读取
        }
        List<List<Object>> records = new ArrayList<List<Object>>();
        Sheet _sheet = workbook.getSheetAt(0);
        List<Object> tmp = null;
        Row _row = null;
        for (int i = 0; i < length; i++) {
            if (mark > maxLength) {
                break;
            }
            _row = _sheet.getRow(mark);
            if (_row != null) {
                tmp = new ArrayList<Object>();
                for (int j = 0; j < _row.getLastCellNum(); j++) {
                    // 空单元格也算作一个属性
                    tmp.add(PoiUtils.getValue(_row.getCell(j)));
                }
            }
            records.add(tmp);
            mark++;
        }
        return records;
    }

    @Override
    public Map<String, Object> read(List<String> fields) {
        List<Map<String, Object>> list = read(fields, 1);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<Map<String, Object>> read(List<String> fields, int length) {
        if (CollectionUtils.isEmpty(fields) || length <= 0 || !hasMore()) {
            log.error("ExcelReader.read@param[fields]不能为空!");
            return null;
        }
        List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
        Sheet _sheet = workbook.getSheetAt(0);
        Map<String, Object> tmp = null;
        Row _row = null;
        for (int i = 0; i < length; i++) {
            if (mark > maxLength) {
                break;
            }
            _row = _sheet.getRow(mark);
            if (_row != null) {
                tmp = new HashMap<String, Object>();
                for (int j = 0; j < _row.getLastCellNum(); j++) {
                    if (j >= fields.size()) {
                        break;
                    }
                    if (StringUtils.isNotBlank(fields.get(j))) {
                        tmp.put(fields.get(j), PoiUtils.getValue(_row.getCell(j)));
                    }
                }
            }
            records.add(tmp);
            mark++;
        }
        return records;
    }

    @Override
    public int getMark() {
        return mark;
    }

    @Override
    public void close() {
        if (workbook != null) {
            workbook = null;
        }
    }

}
