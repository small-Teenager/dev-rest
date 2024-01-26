package com.dev.rest.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author zhangyaodong
 * @version 1.0
 * @description
 * @create 2024/1/25 20:13
 */
public class MyLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = null;
        String acceptLanguage = request.getHeader("Accept-Language");
        // zh-CN,zh;q=0.9,en;q=0.8
        //如果参数带了locale就用参数的, 如果没带就用请求头的(浏览器)
        if (StringUtils.isNotEmpty(acceptLanguage)) {
            String[] languageArr = acceptLanguage.split(",");
            String[] localeArr = languageArr[0].split("-");
            //第一个参数是语言, 第二个参数是国家
            locale = new Locale(localeArr[0], localeArr[1]);
        } else {
            locale = request.getLocale();
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
