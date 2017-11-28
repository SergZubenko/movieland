package com.sergzubenko.movieland.web.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class EntityDtoReflectionMapper {

    public static <T, S> T map(S src, Class<T> destClass) {
        try {
            return map0(src, destClass);
        } catch (IllegalAccessException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Cant map Dto", e);
        }
    }

    public static <T> List<T> mapList(List<?> srcList, Class<T> destClass) {
        List<T> destList = new ArrayList<>();
        for (Object o : srcList) {
            destList.add(map(o, destClass));
        }
        return destList;
    }

    @SuppressWarnings("unchecked")
    private static <T, S> T map0(S src, Class<T> destClass) throws IllegalAccessException, NoSuchFieldException, InstantiationException, ClassNotFoundException {

        if (src == null){
            return null;
        }

        Class srcClass = src.getClass();
        if (srcClass.equals(destClass)) {
            return (T) src;
        }
        T dto = destClass.newInstance();
        for (Field destField : destClass.getDeclaredFields()) {
            String fieldName = destField.getName();
            Field srcField;
            srcField = srcClass.getDeclaredField(fieldName);

            srcField.setAccessible(true);
            Object srcValue = srcField.get(src);
            srcField.setAccessible(false);

            if (srcValue == null){
                continue;
            }

            if (List.class.isAssignableFrom(destField.getType())) {
                List srcColl = (List) srcValue;
                Class<?> clazz = Class.forName(((ParameterizedType) destField.getGenericType()).getActualTypeArguments()[0].getTypeName());
                List<Object> list = new ArrayList<>(srcColl.size());
                for (Object o : srcColl) {
                    list.add(map0(o, clazz));
                }
                destField.setAccessible(true);
                destField.set(dto, list);
                destField.setAccessible(false);
            } else {

                destField.setAccessible(true);
                destField.set(dto, map0(srcValue, destField.getType()));
                destField.setAccessible(false);

            }
        }
        return dto;
    }


}
