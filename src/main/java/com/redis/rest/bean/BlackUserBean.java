package com.redis.rest.bean;

import com.redis.rest.vo.BlackUserVO;

import java.io.Serializable;
import java.util.List;

public class BlackUserBean implements Serializable {

    private static final long serialVersionUID = 5411427167261372097L;

    private List<BlackUserVO> blackUserList;

    public List<BlackUserVO> getBlackUserList() {
        return blackUserList;
    }

    public void setBlackUserList(List<BlackUserVO> blackUserList) {
        this.blackUserList = blackUserList;
    }
}
