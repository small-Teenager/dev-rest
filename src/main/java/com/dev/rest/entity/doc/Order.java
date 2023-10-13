package com.dev.rest.entity.doc;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单信息
 */
@Document("order")
// 联合索引
//@CompoundIndexes({@CompoundIndex(name = "idx_shipper_shipperPhone", def = "{'shipper':1,'shipperPhone':1}")})
public class Order implements Serializable {

    private static final long serialVersionUID = -6882617429800890136L;
    @Id
    private String _id;

    /**
     * 订单id
     */
    @Indexed(name = "uni_orderId", unique = true)
    private Long orderId;
    /**
     * 状态
     * @see com.dev.rest.enums.LogisticsStatusEnum
     */
    private Integer status;
    /**
     * 下单时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;
    /**
     * 发货人
     */
    private String shipper;
    /**
     * 发货地址
     */
    private String shippingAdress;
    /**
     * 发货人手机
     */
    private String shipperPhone;
    /**
     * 发货时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date shipTime;
    /**
     * 接收人
     */
    private String recevier;
    /**
     * 接收地址
     */
    private String recevierAddress;
    /**
     * 接收人号码
     */
    private String recevierPhone;
    /**
     * 物流信息
     */
    private List<Logistics> logistics;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_Id() {
        return _id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getShippingAdress() {
        return shippingAdress;
    }

    public void setShippingAdress(String shippingAdress) {
        this.shippingAdress = shippingAdress;
    }

    public String getShipperPhone() {
        return shipperPhone;
    }

    public void setShipperPhone(String shipperPhone) {
        this.shipperPhone = shipperPhone;
    }

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }

    public String getRecevier() {
        return recevier;
    }

    public void setRecevier(String recevier) {
        this.recevier = recevier;
    }

    public String getRecevierAddress() {
        return recevierAddress;
    }

    public void setRecevierAddress(String recevierAddress) {
        this.recevierAddress = recevierAddress;
    }

    public String getRecevierPhone() {
        return recevierPhone;
    }

    public void setRecevierPhone(String recevierPhone) {
        this.recevierPhone = recevierPhone;
    }

    public List<Logistics> getLogistics() {
        return logistics;
    }

    public void setLogistics(List<Logistics> logistics) {
        this.logistics = logistics;
    }
}
