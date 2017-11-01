package com.sergzubenko.movieland.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class SortedColumnMapper {

    public String getColumnName(Class clazz, String param) {
        Field[] declaredFields = clazz.getDeclaredFields();
        Field foundField=null;
        for (Field declaredField : declaredFields) {
            if (declaredField.getName().equals(param)){
                foundField = declaredField;
            }
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
        if (foundField==null){
            throw new NoSuchFieldError(param + " column name is not allowed here");
        }
        return foundField.getName();
    }

}
