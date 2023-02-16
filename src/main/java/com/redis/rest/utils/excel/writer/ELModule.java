package com.redis.rest.utils.excel.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Description: 表达式语言导出模板接口（专用于导出EL表达式的模板， 即单元格中数据、图片、公式都是通过表达式语言描述的）
 * File Name: ELModule.java
 */
public interface ELModule extends ExportModule {
	
	/**
	 * 按模板设定导出Bean对象到指定输出流
	 * 
	 * @param bean    封装各项数据的Bean对象
	 * @param format  导出格式（1: xls[1997-2003]; 2: xlsx[2007+]）
	 * @param stream  输出流
	 */
	 void export(Serializable bean, int format, OutputStream stream) throws IOException;
	
}