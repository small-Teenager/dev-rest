package com.dev.rest.dto;

import com.dev.rest.annotation.EnumValue;
import com.dev.rest.annotation.ValidGroup;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Negative;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author: yaodong zhang
 * @create 2023/2/2
 */
public class ProjectDTO implements Serializable {

    private static final long serialVersionUID = -427141287680248551L;

    @NotBlank(message = "ID不能为空", groups = {ValidGroup.Update.class})
    private String id;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]", message = "只允许输入数字和字母")
    private String strValue;

    @Min(value = -99, message = "值不能小于-99")
    @Max(value = 100, message = "值不能超过100")
    private Integer intValue;

    @Negative(message = "值必须为负数")
    private Integer negativeValue;

    @EnumValue(strValues = {"agree", "refuse"})
    private String strEnum;

    @EnumValue(intValues = {1983, 1990, 2022})
    private Integer intEnum;

    @Valid
    private TeamDTO teamDTO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    public Integer getIntValue() {
        return intValue;
    }

    public void setIntValue(Integer intValue) {
        this.intValue = intValue;
    }

    public Integer getNegativeValue() {
        return negativeValue;
    }

    public void setNegativeValue(Integer negativeValue) {
        this.negativeValue = negativeValue;
    }

    public String getStrEnum() {
        return strEnum;
    }

    public void setStrEnum(String strEnum) {
        this.strEnum = strEnum;
    }

    public Integer getIntEnum() {
        return intEnum;
    }

    public void setIntEnum(Integer intEnum) {
        this.intEnum = intEnum;
    }

    public TeamDTO getTeamDTO() {
        return teamDTO;
    }

    public void setTeamDTO(TeamDTO teamDTO) {
        this.teamDTO = teamDTO;
    }
}