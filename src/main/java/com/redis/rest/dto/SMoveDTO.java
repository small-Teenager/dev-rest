package com.redis.rest.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author: yaodong zhang
 * @create 2022/12/26
 */
public class SMoveDTO implements Serializable {

    private static final long serialVersionUID = 8390511955812276107L;
    @NotEmpty(message = "请输入member")
    private String member ;

    @NotEmpty(message = "请输入source")
    private String source;

    @NotEmpty(message = "请输入destination")
    private String destination  ;

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
