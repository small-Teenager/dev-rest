package com.dev.rest.enums;

public enum LogisticsStatusEnum {

    TO_SHIP("待发货", 1),
    PROCESSING("处理中", 2),

    PACKAGED("已打包", 3),
    OUTBOUND("已出库", 4);

    private String desc;
    private int code;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    LogisticsStatusEnum(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }
}
