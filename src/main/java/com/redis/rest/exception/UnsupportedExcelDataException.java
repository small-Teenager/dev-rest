package com.redis.rest.exception;

public class UnsupportedExcelDataException extends Exception {

	private static final long serialVersionUID = 7333553524549707383L;
	
	public static final int ILLEGAL_PARAMS = 1;
	
	public static final int EXCEED_ROW_COUNT_LIMIT_ERROR = 1 << 1;
	public static final int EXCEED_COLUMN_COUNT_LIMIT_ERROR = 1 << 2;
	
	public static final int UNRECOGNIZED_CELL_DATA_ERROR = 1 << 3;
	
	private int errType;
	
	public UnsupportedExcelDataException(int err) {
		this.errType = err;
	}
	
	public int getErrType() {
		return errType;
	}
	
	public String getErrMsg() {
		switch (errType) {
			case EXCEED_ROW_COUNT_LIMIT_ERROR:
				return "超出最大行限制";
			case EXCEED_COLUMN_COUNT_LIMIT_ERROR:
				return "超出最大列限制";
			case UNRECOGNIZED_CELL_DATA_ERROR:
				return "超出最大行限制";
			default:
				return "未知错误";
		}
	}

}
