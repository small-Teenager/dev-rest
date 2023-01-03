package com.redis.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class LRemDTO implements Serializable {

    private static final long serialVersionUID = 2174671843101647747L;

    @NotEmpty(message = "请输入key")
    private String key;

    @NotNull(message = "请输入count")
    private Long count;

    @NotNull(message = "请输入value")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}


