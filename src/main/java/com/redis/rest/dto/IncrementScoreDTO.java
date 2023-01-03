package com.redis.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class IncrementScoreDTO implements Serializable {

    private static final long serialVersionUID = 7587651001768892384L;

    @NotEmpty(message = "请输入key")
    private String key;

    @NotNull(message = "请输入value")
    private String value;

    @NotNull(message = "请输入score")
    private Double score;

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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
