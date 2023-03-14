package com.redis.rest.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author: yaodong zhang
 * @create 2022/12/23
 */
public class SetSAddDTO implements Serializable {

    private static final long serialVersionUID = -8377969768886337892L;
    @NotEmpty(message = "请输入key")
    private String key;

    @NotEmpty(message = "请输入value")
    private List<String> value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
