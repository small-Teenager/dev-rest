package com.dev.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class StringSetDTO implements Serializable {

    private static final long serialVersionUID = 6805184240310047806L;
    @NotEmpty(message = "请输入key")
    private String key;

    @NotEmpty(message = "请输入value")
    private String value;

    @NotNull(message = "请输入timeout")
    private Long timeout;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

}
