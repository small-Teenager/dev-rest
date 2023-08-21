package com.dev.rest.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author: yaodong zhang
 * @create 2022/12/29
 */

public class HSetDTO implements Serializable {

    private static final long serialVersionUID = -6794200797239662958L;
    @NotEmpty(message = "请输入key")
    private String key;

    @NotEmpty(message = "请输入hashKey")
    private String hashKey;

    @NotEmpty(message = "请输入value")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
