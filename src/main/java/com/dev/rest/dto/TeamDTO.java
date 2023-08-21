package com.dev.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.Year;
import java.util.Date;
import java.util.List;

/**
 * @author: yaodong zhang
 * @create 2023/2/2
 */
public class TeamDTO  implements Serializable {

    private static final long serialVersionUID = 3264245525407030730L;

    @FutureOrPresent(message = "只能输入当前年份或未来的年份")
    private Year nowYear;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Future(message = "只能是未来的时间")
    private Date futureTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Past(message = "只能是过去的时间")
    private Date pastTime;

    @Email(message = "请输入正确的邮箱")
    private String email;

    @Valid
    private List<MemberDTO> list;

    public Year getNowYear() {
        return nowYear;
    }

    public void setNowYear(Year nowYear) {
        this.nowYear = nowYear;
    }

    public Date getFutureTime() {
        return futureTime;
    }

    public void setFutureTime(Date futureTime) {
        this.futureTime = futureTime;
    }

    public Date getPastTime() {
        return pastTime;
    }

    public void setPastTime(Date pastTime) {
        this.pastTime = pastTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<MemberDTO> getList() {
        return list;
    }

    public void setList(List<MemberDTO> list) {
        this.list = list;
    }
}
