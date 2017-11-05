package com.sergzubenko.movieland.persistence.jdbc.util;

import java.lang.reflect.Field;
import java.util.Map;

public class OrderByAppender {

     String getColumnName(Class clazz, String param) {
        Field[] declaredFields = clazz.getDeclaredFields();
        Field foundField = null;
        for (Field declaredField : declaredFields) {
            if (declaredField.getName().equals(param)) {
                foundField = declaredField;
            }
            Sorted annotation = declaredField.getAnnotation(Sorted.class);
            if (annotation != null) {
                String columnName = annotation.value();
                if ("".equals(columnName)) {
                    columnName = declaredField.getName();
                }
                if (columnName.equals(param)) {
                    return declaredField.getName();
                }
            }
        }
        if (foundField == null) {
            throw new NoSuchFieldError(param + " column name is not allowed here");
        }
        return foundField.getName();
    }

    public String prepareOrderedQuery(String SQL, String OrderByPrefix, Class clazz, Map<String, String> params) {

        if (params == null || params.size() == 0) {
            return SQL;
        }

        StringBuilder stringBuilder = new StringBuilder(SQL);
        stringBuilder.append(' ');
        stringBuilder.append(OrderByPrefix);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String columnName = getColumnName(clazz, entry.getKey());
            String ascDesc = SortOrder.getByName(entry.getValue()).name();
            stringBuilder.append(' ');
            stringBuilder.append(columnName);
            stringBuilder.append(' ');
            stringBuilder.append(ascDesc);
            stringBuilder.append(',');

        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
