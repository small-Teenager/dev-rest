package com.dev.rest.entity;

import com.dev.rest.common.annotation.SensitiveWrapped;
import com.dev.rest.common.entity.BaseEntity;
import com.dev.rest.common.enums.SensitiveEnum;

import java.io.Serializable;

/**
 * @author zhangyaodong
 * @version 1.0
 * @description
 */

public class UserInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1207456890976974089L;
    private Long id;

    /**
     * 中文名
     */
    @SensitiveWrapped(SensitiveEnum.CHINESE_NAME)
    private String chineseName;

    /**
     * 身份证号
     */
    @SensitiveWrapped(SensitiveEnum.ID_CARD)
    private String idCard;

    /**
     * 座机号
     */
    @SensitiveWrapped(SensitiveEnum.FIXED_PHONE)
    private String fixedPhone;

    /**
     * 手机号
     */
    @SensitiveWrapped(SensitiveEnum.MOBILE_PHONE)
    private String mobilePhone;

    /**
     * 地址
     */
    @SensitiveWrapped(SensitiveEnum.ADDRESS)
    private String address;

    /**
     * 电子邮件
     */
    @SensitiveWrapped(SensitiveEnum.EMAIL)
    private String email;

    /**
     * 银行卡
     */
    @SensitiveWrapped(SensitiveEnum.BANK_CARD)
    private String bankCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName == null ? null : chineseName.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getFixedPhone() {
        return fixedPhone;
    }

    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone == null ? null : fixedPhone.trim();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }

}
