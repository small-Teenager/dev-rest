package com.dev.rest.utils.excel.writer.module;

import com.dev.rest.utils.excel.PoiUtils;
import com.dev.rest.utils.excel.writer.ListDataModule;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

/**
 * Description: 通过POI处理List类型数据导出的模板
 * File Name: PoiListDataModule.java
 */
public class PoiListDataModule implements ListDataModule {

    private static final String templateLocation = "/template/";  // 模板存放位置

    private File moduleFile;

    private Workbook workbook;

    private String moduleName;
    private int format;   // 1: xls; 2: xlsx

    private Short headerHeight;
    private Short rowHeight;

    private CellStyle headerStyle;
    private CellStyle[][] bodyStyle;

    public PoiListDataModule(String moduleName, int format) throws FileNotFoundException, IOException {
        this.moduleName = moduleName;
        this.format = format == 1 ? 1 : 2;

        loadModule(moduleName);  // 加载模板
        parse();                 // 解析模板
    }

    public String getModuleName() {
        return moduleName;
    }

    public int getFormat() {
        return format;
    }

    public Short getHeaderHeight() {
        return headerHeight;
    }

    public Short getRowHeight() {
        return rowHeight;
    }

    public CellStyle getHeaderStyle() {
        return headerStyle;
    }

    public CellStyle[][] getBodyStyle() {
        return bodyStyle;
    }

    @Override
    public void parse() {
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet.getPhysicalNumberOfRows() == 0) {
            return;
        }
        int lastRowIndex = sheet.getLastRowNum();
        int i = 0;
        Row _row = null;
        for (; i <= lastRowIndex; i++) {
            _row = sheet.getRow(i);
            if (_row.getPhysicalNumberOfCells() == 0) {
                continue;
            }
            headerHeight = _row.getHeight();
            Cell tmp = PoiUtils.getFirstNotNullCell(_row);
            if (tmp != null) {
                headerStyle = tmp.getCellStyle();
                break;
            }
        }
        if (i < lastRowIndex) {
            int[] region = PoiUtils.getMaxPhysicalRegion(sheet, 1, lastRowIndex);
            if (region != null && region.length == 2 && region[0] > 0 && region[1] > 0) {
                // 表体列样式的设置（0：无样式表体, 1: 单一设置的表体, 2: 包括主栏的表体, 3: 设置尾列的表体）
                bodyStyle = new CellStyle[region[0]][region[1]];
                int x = 0;
                Row row = null;
                for (i = i + 1; i <= lastRowIndex; i++) {
                    row = sheet.getRow(i);
                    int y = 0;
                    if (row.getPhysicalNumberOfCells() == 0) {
                        continue;
                    }
                    if (x == 0) {
                        rowHeight = row.getHeight();
                    }
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        if (row.getCell(j) != null && StringUtils.isNotBlank(row.getCell(j).getStringCellValue())) {
                            bodyStyle[x][y] = row.getCell(j).getCellStyle();
                            y++;
                        }
                    }
                    x++;
                }
            }
        }
    }

    @Override
    public List<String> read(int i) throws IOException {
        List<String> cells = new ArrayList<String>();
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null || sheet.getPhysicalNumberOfRows() <= i) {
            return cells;
        }
        Row row = sheet.getRow(i);
        if (row == null || row.getPhysicalNumberOfCells() == 0) {
            return cells;
        }
        for (int j = 0; j < row.getLastCellNum(); j++) {
            if (row.getCell(j) == null || StringUtils.isBlank(row.getCell(j).getStringCellValue())) {
                cells.add("");
            } else {
                cells.add(row.getCell(j).getStringCellValue());
            }
        }
        return cells;
    }

    @Override
    public void export(LinkedHashMap<String, String> titles, List<Map<String, ?>> data, OutputStream stream) throws IOException {
        Sheet sheet = workbook.createSheet();
        // 表头渲染
        Row header = sheet.createRow(0);
        int idx = 0;
        String _title = null;
        Cell _headerCell = null;
        boolean hideHeader = true;
        for (Iterator<String> iterator = titles.values().iterator(); iterator.hasNext(); idx++) {
            _headerCell = header.createCell(idx);
            _title = iterator.next();
            _headerCell.setCellValue(_title);
            if (headerStyle != null) {
                _headerCell.setCellStyle(headerStyle);
            }
            hideHeader = hideHeader && StringUtils.isBlank(_title);
        }
        if (hideHeader) {
            header.setZeroHeight(true); // 隐藏表头
        } else if (headerHeight != null) {
            header.setHeight(headerHeight);
        }

        // 表体渲染
        if (data != null && data.size() > 0) {
            Row _row = null;
            Map<String, ?> _rowData = null;
            CellStyle[] _rowStyles = null;  // 当前行应用的样式（单元格样式数组）
            Cell _bodyCell = null;
            String _key;
            for (int i = 0; i < data.size(); i++) {
                _rowData = data.get(i);
                _row = sheet.createRow(i + 1);
                if (bodyStyle != null && bodyStyle.length > 0) {
                    int _styleIndex = (i + 1) % bodyStyle.length - 1;
                    if (_styleIndex == -1) {
                        _styleIndex = bodyStyle.length - 1;
                    }
                    _rowStyles = bodyStyle[_styleIndex];
                }
                int j = 0;
                for (Iterator<String> iterator = titles.keySet().iterator(); iterator.hasNext(); j++) {
                    _bodyCell = _row.createCell(j);
                    _key = iterator.next();
                    PoiUtils.assignValue(_bodyCell, _rowData.get(_key));
                    if (_rowStyles != null && _rowStyles.length > 0) {
                        if (_rowStyles.length == 1) {
                            if (_rowStyles[0] != null) {
                                _bodyCell.setCellStyle(_rowStyles[0]);
                            }
                        } else if (_rowStyles.length == 2) {
                            if (j == 0) {
                                if (_rowStyles[0] != null) {
                                    _bodyCell.setCellStyle(_rowStyles[0]);
                                }
                            } else {
                                if (_rowStyles[1] != null) {
                                    _bodyCell.setCellStyle(_rowStyles[1]);
                                }
                            }
                        } else {
                            if (j == 0) {
                                if (_rowStyles[0] != null) {
                                    _bodyCell.setCellStyle(_rowStyles[0]);
                                }
                            } else if (j == titles.size()) {
                                if (_rowStyles[2] != null) {
                                    _bodyCell.setCellStyle(_rowStyles[2]);
                                }
                            } else {
                                if (_rowStyles[1] != null) {
                                    _bodyCell.setCellStyle(_rowStyles[1]);
                                }
                            }
                        }
                    }
                }
            }
        }

        // 宽度调整
        for (int i = 0; i < titles.size(); i++) {
            sheet.autoSizeColumn(i); // 自适应宽度, 含汉字的列略有不准
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 2);
        }

        // 删除模板Sheet
        workbook.removeSheetAt(0);

        workbook.write(stream);
        stream.close();
        stream = null;
    }

    @Override
    public String transformToCss() {
        StringBuffer cssText = new StringBuffer();
        String lineSeparator = System.getProperty("line.separator");
        String tableCssName = "table." + moduleName;
        cssText.append(tableCssName).append(" { border-collapse: collapse; border: 0px; }").append(lineSeparator);
        cssText.append(tableCssName).append(" td { padding-left: 3px; }").append(lineSeparator);
        if (headerStyle != null) {
            cssText.append(tableCssName).append(" > thead td {");
            cssText.append("height:").append(headerHeight / 20).append("px;");
            cssText.append(PoiUtils.transformCellStyleToCss(workbook, headerStyle));
            cssText.append("}").append(lineSeparator);
        }
        if (bodyStyle != null) {
            int x = bodyStyle.length;
            int y = bodyStyle[0] != null ? bodyStyle[0].length : 0;
            String tdStyleName = null;
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (bodyStyle[i][j] == null) {
                        continue;
                    }
                    tdStyleName = j == 0 ? "td:first-child" : (j == 1 ? "td" : "td:last-child");
                    cssText.append(tableCssName).append(" > tbody tr:nth-child(").append(x).append("n+").append(i == x - 1 ? 0 : i + 1).append(") ").append(tdStyleName).append(" {");
                    cssText.append(PoiUtils.transformCellStyleToCss(workbook, bodyStyle[i][j]));
                    cssText.append("}").append(lineSeparator);
                }
            }
        }
        return cssText.toString();
    }

    @Override
    public File getModuleFile() {
        return this.moduleFile;
    }

    /**
     * 加载模板文件
     *
     * @param module 模板文件名称
     * @return Workbook
     */
    private void loadModule(String module) throws FileNotFoundException, IOException {
        // 获取模板路径
        String basePath = this.getClass().getResource("/").getPath();
        basePath = basePath.replace("test-classes", "classes");
        if (basePath.indexOf("/") == 0 && basePath.indexOf(":") > 0) {
            basePath = basePath.substring(1, basePath.lastIndexOf("/"));
        } else {
            basePath = basePath.substring(0, basePath.lastIndexOf("/"));
        }
        String modulePath = new StringBuffer(basePath).append(templateLocation).append(module).append(format == 1 ? ".xls" : ".xlsx").toString();
        File file = new File(modulePath);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException();
        }
        moduleFile = file;
        workbook = format == 1 ? new HSSFWorkbook(new FileInputStream(modulePath)) : new XSSFWorkbook(new FileInputStream(modulePath));
    }

}
