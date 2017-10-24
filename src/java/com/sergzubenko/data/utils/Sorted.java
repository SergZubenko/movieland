package com.sergzubenko.data.utils;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sergz on 24.10.2017.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value= ElementType.FIELD)
public @interface Sorted {

    String value() default "";

    @AliasFor("value")
    String columnName() default "";;
}
