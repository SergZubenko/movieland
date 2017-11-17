package com.sergzubenko.movieland.persistence.jdbc.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value= ElementType.FIELD)
@interface Sorted {
    String value() default "";

    String databaseColumnName() default "";
}
