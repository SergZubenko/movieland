package com.sergzubenko.movieland.web.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class ObjectTransformer {
    private static final Logger log = LoggerFactory.getLogger("EntityDtoReflectionMapper");

    public static <T, S> T transform(S src, Class<T> destClass) {
        try {
            return copyTransformObject(src, destClass);
        } catch (Exception e) {
            log.error("Can't copy object", e);
            throw new RuntimeException("Can't copy object", e);
        }
    }

    public static <T> List<T> transformList(List<?> srcList, Class<T> destClass) {
        List<T> destList = new ArrayList<>();
        for (Object o : srcList) {
            destList.add(transform(o, destClass));
        }
        return destList;
    }

    @SuppressWarnings("unchecked")
    private static <T, S> T copyTransformObject(S src, Class<T> destClass) throws Exception{

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

            if (fieldSkipped(destField)){
                continue;
            }

            Field srcField = findFieldInClass(srcClass, fieldName);
            if(srcField == null || fieldSkipped(srcField)){
                continue;
            }

            Object srcValue = getFieldValue(srcField, src);
            if (srcValue == null){
                continue;
            }

            TransformTo transformTo = srcField.getAnnotation(TransformTo.class);
            Object destValue;
            if (List.class.isAssignableFrom(destField.getType())) {
                destValue = copyTransformList((List)srcValue, destField, transformTo);
            } else {
                destValue = copyTransformObject(transformTo(srcValue, transformTo), destField.getType());
            }
            setFiledValue(destField, dto, destValue);
        }
        return dto;
    }

    private static List copyTransformList(List sourceCollection, Field destField, TransformTo transformTo) throws Exception {
        Class<?> clazz = getGenericBaseType(destField);
        List<Object> list = new ArrayList<>(sourceCollection.size());
        for (Object o : sourceCollection) {
            list.add(copyTransformObject(transformTo(o, transformTo), clazz));
        }
        return list;
    }

    private static Object transformTo(Object o, TransformTo formula) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        if (formula == null){
            return o;
        }
        Object transformed = formula.clazz().newInstance();
        Field field = transformed.getClass().getDeclaredField(formula.field());
        setFiledValue(field, transformed, o);
        return transformed;
    }

    private static Object getFieldValue(Field field, Object instance) throws IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(instance);
        field.setAccessible(false);
        return value;
    }

    private static void setFiledValue(Field field, Object instance, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(instance,value);
        field.setAccessible(false);
    }

    private static boolean fieldSkipped(Field field){
        if (field.isAnnotationPresent(Skip.class)){
            log.info("Field {} fieldSkipped", field.getName());
            return true;
        }
        return false;
    }

    private static Class getGenericBaseType(Field field) throws ClassNotFoundException {
        return Class.forName(((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName());
    }

    @Nullable
    private static Field findFieldInClass(Class clazz, String fieldName) {
        for (Field f : clazz.getDeclaredFields()) {
            if(f.getName().equals(fieldName)){
                return  f;
            }
        }
        return null;
    }
}
