package com.dev.rest.dto;


import com.dev.rest.common.annotation.IsMobile;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author: yaodong zhang
 * @create 2023/1/3
 */
public class AddBlackDTO  implements Serializable {

    private static final long serialVersionUID = 6885950046555492789L;
    @NotEmpty(message = "请输入mobile")
    @IsMobile(message = "请输入正确的mobile")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

