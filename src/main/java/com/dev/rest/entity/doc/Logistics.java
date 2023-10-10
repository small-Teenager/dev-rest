package com.dev.rest.entity.doc;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 物流信息
 */
public class Logistics implements Serializable {

    private static final long serialVersionUID = 4697235383841443706L;
    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 操作
     */
    private String operation;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 操作时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operationTime;
    /**
     * 地址
     */
    private String address;
    /**
     * 备注细节
     */
    private String details;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Logistics() {
    }

    public Logistics(Long orderId, String operation, String operator, Date operationTime, String address, String details) {
        this.orderId = orderId;
        this.operation = operation;
        this.operator = operator;
        this.operationTime = operationTime;
        this.address = address;
        this.details = details;
    }
}
