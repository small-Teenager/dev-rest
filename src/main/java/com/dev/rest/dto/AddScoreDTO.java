package com.dev.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: yaodong zhang
 * @create 2022/12/30
 */
public class AddScoreDTO implements Serializable {

    private static final long serialVersionUID = -6382099508029298857L;

    @NotEmpty(message = "请输入value")
    private String value;

    @NotNull(message = "请输入score")
    private Double score;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
