package com.dev.rest.config.DataSource;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {

    DBTypeEnum type() default DBTypeEnum.MASTER;

}
