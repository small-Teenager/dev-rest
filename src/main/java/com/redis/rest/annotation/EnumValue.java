package com.redis.rest.annotation;

import com.redis.rest.validator.EnumValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Constraint(validatedBy = {EnumValueValidator.class})
public @interface EnumValue {

    /**
     * 是否需要（true:不能为空，false:可以为空）
     */
    boolean isRequire() default false;

    /**
     * 字符串数组
     */
    String[] strValues() default {};

    /**
     * int数组
     */
    int[] intValues() default {};

    /**
     * 枚举类
     */
    Class<?>[] enumClass() default {};

    String message() default "所传参数不在允许的值范围内";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        EnumValue[] value();
    }
}
