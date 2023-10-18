package com.dev.rest.entity;

import com.dev.rest.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Black extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 806395307614627623L;
    private Long id;

    private String mobile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
