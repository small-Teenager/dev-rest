package com.dev.rest.response;



import java.io.Serializable;

/**
 * 返回数据类型
 */
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 5748957490055835898L;

    public static final int SUCCESS_CODE = 200;

    public static final String SUCCESS_VALUE = "success";
    public static final int FAIL_CODE = 500;

    private int code;
    private String msg;
    private T data;

    private long timestamp;

    public ApiResponse() {
    }

    public ApiResponse(T data) {
        this.data = data;
        this.code = SUCCESS_CODE;
        this.msg = SUCCESS_VALUE;
        this.timestamp = timestamp;
    }

    public ApiResponse(String msg, T data) {
        this.msg = msg;
        this.data = data;
        this.timestamp = timestamp;
    }

    public static ApiResponse success(String msg, Object data) {
        return new ApiResponse(msg, data);
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse(data);
    }

    public static ApiResponse error(int code, String msg) {
        return new ApiResponse(code, msg);
    }

    public ApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.timestamp = timestamp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }
}
