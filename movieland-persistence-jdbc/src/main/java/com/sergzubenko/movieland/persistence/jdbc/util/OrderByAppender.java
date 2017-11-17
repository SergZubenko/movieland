package com.sergzubenko.movieland.persistence.jdbc.util;

import java.lang.reflect.Field;
import java.util.Map;

/*An object that returns order by clause by given map of params.
* Please use Map with saved order (like LinkedHashMap) of entries to get correct order of parameters
* */
public class OrderByAppender {

    /*Return ORDER BY clause by given params
    */
    public static String prepareOrderedQuery(String SQL, String OrderByPrefix, Class clazz, Map<String, String> params) {

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

    /**
     * Returns column name by given value.
     * <p>Given column name can be overridden by annotation <tt>Sorted</tt>
     *  please use databaseColumnName value of <tt>Sorted</tt> annotation in case database
     * </p>
     */
     static String getColumnName(Class clazz, String param) {
        Field[] declaredFields = clazz.getDeclaredFields();
        Field foundField = null;
        for (Field declaredField : declaredFields) {
            if (declaredField.getName().equals(param)) {
                foundField = declaredField;
            }
            Sorted annotation = declaredField.getAnnotation(Sorted.class);
            if (annotation != null) {
                String columnName = annotation.value();
                if (columnName.isEmpty()) {
                    columnName = declaredField.getName();
                }
                if (columnName.equals(param)) {
                    return annotation.databaseColumnName().isEmpty()?declaredField.getName():annotation.databaseColumnName();
                }
            }
        }
        if (foundField == null) {
            throw new NoSuchFieldError(param + " column name is not allowed here");
        }
        return foundField.getName();
    }
}
