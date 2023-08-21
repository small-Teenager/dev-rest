package com.dev.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ExpireDTO implements Serializable {

    private static final long serialVersionUID = -7859903712151996378L;
    @NotEmpty(message = "请输入key")
    private String key;

    @NotNull(message = "请输入timeout")
    private Long timeout;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }
}
