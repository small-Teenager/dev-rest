package com.dev.rest.annotation;

import com.dev.rest.enums.SensitiveEnum;
import com.dev.rest.validator.SensitiveSerialize;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerialize.class)
public @interface SensitiveWrapped {

    /**
     * 脱敏类型
     * @return
     */
    SensitiveEnum value();
}

