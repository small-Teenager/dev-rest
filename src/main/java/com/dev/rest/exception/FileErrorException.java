package com.dev.rest.exception;

/**
 * @author: yaodong zhang
 * @create 2023/2/15
 */
public class FileErrorException extends Exception {

    private static final long serialVersionUID = -7729683727415612039L;

    public static final int FILE_NOT_FOUND = 1 << 1;
    public static final int FILE_EXTENSION_ERROR = 1 << 2;

    private String suffix;  // 文件后缀或文件类型
    private int errType;  // 错误类型

    public FileErrorException(String suffix, int error) {
        this.suffix = suffix;
        this.errType = error;
    }

    public FileErrorException(int error) {
        this.errType = error;
    }

    public String getSuffix() {
        return suffix;
    }

    public int getErrType() {
        return errType;
    }

    public String getErrMsg() {
        switch (errType) {
            case FILE_NOT_FOUND:
                return "文件不存在";
            case FILE_EXTENSION_ERROR:
                return "文件扩展名不符合规范";
            default:
                return "未知错误";
        }
    }
}

