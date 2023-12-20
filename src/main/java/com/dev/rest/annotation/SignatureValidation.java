package com.dev.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangyaodong
 * @version 1.0
 * @description 开放接口验证签名注解,对外开放的接口加此注解
 * @create 2023/12/20 19:50
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SignatureValidation {
}
