package com.dev.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DecrDTO implements Serializable {

    private static final long serialVersionUID = -2678336858627631188L;
    @NotEmpty(message = "请输入key")
    private String key;

    @NotNull(message = "请输入delta")
    private Long delta;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getDelta() {
        return delta;
    }

    public void setDelta(Long delta) {
        this.delta = delta;
    }
}
