package com.dev.rest.dto;


import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author zhangyaodong
 * @version 1.0
 * @description 用户注册
 * @create 2023/12/19 11:22
 */
public class UserLoginDTO implements Serializable {

    private static final long serialVersionUID = 9203155939366617933L;

    @NotBlank(message = "{account.name}")
    private String name;

    @NotBlank(message = "{password}")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
