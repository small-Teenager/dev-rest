package com.redis.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AppendDTO implements Serializable {

    private static final long serialVersionUID = -4855614728386573206L;

    @NotEmpty(message = "请输入key")
    private String key;

    @NotNull(message = "请输入value")
    private String value;

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
}
