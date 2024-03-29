package com.dev.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: yaodong zhang
 * @create 2022/12/30
 */
public class PFAddDTO implements Serializable {

    private static final long serialVersionUID = -3526023852828177945L;

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
