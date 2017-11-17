package com.sergzubenko.movieland.web.security;

import com.sergzubenko.movieland.entity.UserRole;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface HasRole  {

    UserRole[] value() default {};

}
