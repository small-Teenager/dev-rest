package com.dev.rest.utils;

import java.io.Serializable;

/**
 * @author zhangyaodong
 * @version 1.0
 * @description
 * @create 2023/12/26 09:49
 */
public class PluginConfig implements Serializable {

    private static final long serialVersionUID = 6075127247728965776L;
    /**
     * 插件id
     */
    private String id;
    /**
     * 插件名称
     */
    private String name;
    /**
     * 插件包名+类名
     */
    private String className;
    /**
     * 插件地址
     */
    private String jarRemoteUrl;

    /**
     * 是否激活
     */
    private boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getJarRemoteUrl() {
        return jarRemoteUrl;
    }

    public void setJarRemoteUrl(String jarRemoteUrl) {
        this.jarRemoteUrl = jarRemoteUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
