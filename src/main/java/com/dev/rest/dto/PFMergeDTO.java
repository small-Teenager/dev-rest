package com.dev.rest.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author: yaodong zhang
 * @create 2022/12/30
 */
public class PFMergeDTO implements Serializable {

    private static final long serialVersionUID = -6882483234461017092L;

    @NotEmpty(message = "请输入destination")
    private String destination;

    @NotEmpty(message = "请输入sourceKey1")
    private String sourceKey1;

    @NotEmpty(message = "请输入sourceKey2")
    private String sourceKey2;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSourceKey1() {
        return sourceKey1;
    }

    public void setSourceKey1(String sourceKey1) {
        this.sourceKey1 = sourceKey1;
    }

    public String getSourceKey2() {
        return sourceKey2;
    }

    public void setSourceKey2(String sourceKey2) {
        this.sourceKey2 = sourceKey2;
    }
}
