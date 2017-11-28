package com.sergzubenko.movieland.web.security.annotation;

import com.sergzubenko.movieland.entity.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface HasRole {

    UserRole[] value() default {};

}
