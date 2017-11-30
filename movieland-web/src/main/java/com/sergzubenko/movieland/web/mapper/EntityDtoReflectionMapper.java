package com.sergzubenko.movieland.web.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class EntityDtoReflectionMapper {
    private static final Logger logger = LoggerFactory.getLogger("EntityDtoReflectionMapper");

    public static <T, S> T map(S src, Class<T> destClass) {
        try {
            return mapObject(src, destClass);
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
    private static <T, S> T mapObject(S src, Class<T> destClass) throws IllegalAccessException, NoSuchFieldException, InstantiationException, ClassNotFoundException {

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
            if (destField.isAnnotationPresent(Skip.class)){
                logger.info("Field {} skipped", fieldName);
                continue;
            }

            Field srcField;

            try {
                srcField = srcClass.getDeclaredField(fieldName);
            }
            catch(NoSuchFieldException e){
                logger.warn("Field {} not found in source object ", fieldName);
                continue;
            }

            if (srcField.isAnnotationPresent(Skip.class)){
                logger.info("Field {} skipped", srcField.getName());
                continue;
            }

            srcField.setAccessible(true);


            Object srcValue = srcField.get(src);
            srcField.setAccessible(false);

            if (srcValue == null){
                continue;
            }


            TransformTo transformTo = srcField.getAnnotation(TransformTo.class);

            destField.setAccessible(true);
            if (List.class.isAssignableFrom(destField.getType())) {
                List srcColl = (List) srcValue;
                Class<?> clazz = Class.forName(((ParameterizedType) destField.getGenericType()).getActualTypeArguments()[0].getTypeName());
                List<Object> list = new ArrayList<>(srcColl.size());
                for (Object o : srcColl) {
                    list.add(mapObject(transformTo(o, transformTo), clazz));
                }
                destField.set(dto, list);
            } else {
                destField.set(dto, mapObject(transformTo(srcValue, transformTo), destField.getType()));
            }
            destField.setAccessible(false);
        }
        return dto;
    }


    private static Object transformTo(Object o, TransformTo formula) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        if (formula == null){
            return o;
        }
        Object transformed = formula.clazz().newInstance();
        Field field = transformed.getClass().getDeclaredField(formula.field());
        field.setAccessible(true);
        field.set(transformed, o);
        field.setAccessible(false);
        return transformed;
    }


}
