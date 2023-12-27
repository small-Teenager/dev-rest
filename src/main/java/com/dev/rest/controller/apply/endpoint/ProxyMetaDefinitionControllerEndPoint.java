package com.dev.rest.controller.apply.endpoint;

import com.dev.rest.utils.PluginConfig;
import com.dev.rest.utils.PluginFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zhangyaodong
 * @version 1.0
 * @description
 * @create 2023/12/26 09:42
 */
@RestControllerEndpoint(id = "proxy")
@Component
public class ProxyMetaDefinitionControllerEndPoint {

    @Autowired
    private  PluginFactory pluginFactory;

    /**
     * 查询插件列表信息
     */
    @GetMapping("cachePluginConfigs")
    public Map<String, PluginConfig> getCachePluginConfigs(){
        return pluginFactory.getCachePluginConfigs();
    }

    /**
     * 激活插件
     */
    @PostMapping("{id}")
    public void activePlugin(@PathVariable("id") String id){
        pluginFactory.activePlugin(id);
    }

    /**
     * 保存插件
     * @param pluginConfig
     * @return
     */
    @PostMapping("save")
    public String savePlugin(@RequestBody PluginConfig pluginConfig){
        try {
            pluginFactory.savePlugin(pluginConfig);
            return "success";
        } catch (Exception e) {

        }
        return "fail";

    }

    /**
     * 移除插件
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public String removePlugin(@PathVariable("id")String id){
        try {
            pluginFactory.removePlugin(id);
            return "success";
        } catch (Exception e) {

        }
        return "fail";
    }
}