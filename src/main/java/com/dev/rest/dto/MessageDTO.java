package com.dev.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MessageDTO implements Serializable {

    private static final long serialVersionUID = -4570884181725933007L;
    @JsonProperty("content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
