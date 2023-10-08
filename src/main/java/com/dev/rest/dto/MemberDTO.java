package com.dev.rest.dto;


import com.dev.rest.common.annotation.EnumValue;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: yaodong zhang
 * @create 2023/2/2
 */
public class MemberDTO  implements Serializable {

    private static final long serialVersionUID = -6630459499021345097L;
    @NotBlank(message = "姓名不能为空")
    private String name;

    @EnumValue(intValues = {0, 1, 2}, message = "性别值非法，0：男，1：女，2：其他")
    private Integer sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
