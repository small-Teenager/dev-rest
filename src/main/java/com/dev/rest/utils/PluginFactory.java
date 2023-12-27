package com.dev.rest.utils;

import com.dev.rest.common.autoconfigurer.LogAopService;
import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author zhangyaodong
 * @version 1.0
 * @description
 * @create 2023/12/26 09:46
 */
@Component
public class PluginFactory implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(LogAopService.class);
    private ApplicationContext applicationContext;
    private Map<String, PluginConfig> cachePluginConfigs = new ConcurrentHashMap<>();
    private Map<String, Advice> cachePlugins = new ConcurrentHashMap();


    public Map<String, PluginConfig> getCachePluginConfigs() {
        return cachePluginConfigs;
    }

    public Collection<PluginConfig> savePlugin(PluginConfig pluginConfig) {
        cachePluginConfigs.put(pluginConfig.getId(), pluginConfig);
        if (pluginConfig.isActive()) {
            activePlugin(pluginConfig.getId());
        }
        return cachePluginConfigs.values();
    }

    /**
     * @param
     * @method 激活插件
     */
    public void activePlugin(String id) {
        if (!cachePluginConfigs.containsKey(id)) {
            throw new RuntimeException(String.format("这个插件不存在id=%s", id));
        }
        //获取插件的配置信息
        PluginConfig pluginConfig = cachePluginConfigs.get(id);
        //设置激活状态
        pluginConfig.setActive(true);
        //拿到所有的beanDefinition
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        //为符合条件的bean加上插件
        for (String beanDefinition : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinition);
            //bean是本类跳过
            if (bean == this) {
                continue;
            }
            //bean是null跳过
            if (bean == null) {
                continue;
            }
            //bean
            if (!(bean instanceof Advised)) { // 判断是否属于通知对象
                continue;
            }
            //判断当前bean是否已经有通知拦截器作用，如果有说明已经激活过了直接跳过
            if (find(bean, pluginConfig.getClassName())) {
                continue;
            }
            try {
                //根据插件的配置信息生成通知
                Advice advice = bulidAdvice(pluginConfig);
                //给bean加上通知
                ((Advised) bean).addAdvice(advice);
            } catch (Exception e) {
                log.error("插件添加失败");
                e.printStackTrace();
            }

        }

    }

    /**
     * @param
     * @method 判断当前bean是否已经有通知拦截器作用
     */
    public Boolean find(Object bean, String className) {
        try {
            Advised o = ((Advised) bean);
            for (Advisor advisor : o.getAdvisors()) {
                log.info("当前 bean 所拥有的通知：{}", advisor.getAdvice().getClass().getName());
                if (className.equals(advisor.getAdvice().getClass().getName())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return true;
        }

    }

    /**
     * @param
     * @method remove插件
     */
    public void removePlugin(String id) {
        if (!cachePluginConfigs.containsKey(id)) {
            throw new RuntimeException(String.format("这个插件不存在id=%s", id));
        }
        //获取插件的配置信息
        PluginConfig pluginConfig = cachePluginConfigs.get(id);
        //设置激活状态
        pluginConfig.setActive(true);
        //拿到所有的beanDefinition
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        //为符合条件的bean加上插件
        for (String beanDefinition : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinition);
            if (bean == this) {
                continue;
            }
            if (bean == null) {
                continue;
            }
            if (!(bean instanceof Advised)) { // 判断是否属于通知对象
                continue;
            }
            if (find(bean, pluginConfig.getClassName())) {
                try {
                    Advice advice = bulidAdvice(pluginConfig);
                    //bean加上通知
                    ((Advised) bean).removeAdvice(advice);
                } catch (Exception e) {
                    log.error("插件关闭失败");
                    e.printStackTrace();
                }

            }

        }

    }

    /**
     * @param
     * @method 创建通知
     */
    public Advice bulidAdvice(PluginConfig pluginConfig) {
        Advice plugin = null;
        try {
            Boolean isLoad = false;
            //获取jar包url路径
            URL targetUrl = new URL(pluginConfig.getJarRemoteUrl());
            //获取系统类加载器
            URLClassLoader jarclassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            //遍历所有jar包url比较是否已经加载过插件
            URL[] urLs = jarclassLoader.getURLs();
            for (URL url : urLs) {
                if (targetUrl.equals(url)) {
                    log.info("jar包已经加载过了");
                    isLoad = true;
                    break;
                }
            }
            //没有加载过插件时
            if (!isLoad) {
                //加载jar包
                Method addurl = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addurl.setAccessible(true);
                addurl.invoke(jarclassLoader, targetUrl);
            }
            if (cachePlugins.containsKey(pluginConfig.getName())) {
                return cachePlugins.get(pluginConfig.getName());
            }
            //加载jar包中路径为pluginConfig.getClassName()的类，生成class文件
            Class<?> pluginClass = jarclassLoader.loadClass(pluginConfig.getClassName());
            //通过class生成实例对象
            plugin = (Advice) pluginClass.newInstance();
            cachePlugins.put(pluginConfig.getId(), plugin);

        } catch (MalformedURLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return plugin;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
