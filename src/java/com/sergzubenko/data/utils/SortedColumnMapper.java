package com.sergzubenko.data.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by sergz on 24.10.2017.
 */
public class SortedColumnMapper {

    public String getColumnName(Class clazz, String param) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Annotation annotation = declaredField.getAnnotation(Sorted.class);
            if (annotation != null) {
                String columnName = ((Sorted) annotation).value();
                if ("".equals(columnName)) {
                    columnName = declaredField.getName();
                }
                if (columnName.equals(param)) {
                    return declaredField.getName();
                }
            }
        }
        throw new NoSuchFieldError(param + " column name is not allowed here");
    }

}
