package ru.otus.testFramework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionHelper {
    private ReflectionHelper() {
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.newInstance();
            } else {
                return type.getConstructor(toClasses(args)).newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T>Field[] getFields(T obj){

        return getFields(obj.getClass());
    }

    public static <T> Field[] getFields(Class<T> clazz){
        List<Field> fieldList = new ArrayList<>();
        Class tmpClass = clazz;
        while (tmpClass != null) {
            fieldList.addAll(Arrays.asList(tmpClass.getDeclaredFields()));
            tmpClass = tmpClass .getSuperclass();
        }

        return listToArray(fieldList);
    }

    public static Object getFieldValue(Object object, String name) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }

    public static void setFieldValue(Object object, String name, Object value) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
    }

    static Object callMethod(Object object, String name, Object... args) {
        Method method = null;
        boolean isAccessible = true;
        try {
            method = object.getClass().getDeclaredMethod(name, toClasses(args));
            isAccessible = method.isAccessible();
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (method != null && !isAccessible) {
                method.setAccessible(false);
            }
        }
        return null;
    }


    static <T> Method[] getMethods(Class<T> type){
        return type.getMethods();
    }

    static private Class<?>[] toClasses(Object[] args) {
        List<Class<?>> classes = Arrays.stream(args)
                .map(Object::getClass)
                .collect(Collectors.toList());
        return classes.toArray(new Class<?>[classes.size()]);
    }

    static private Field[] listToArray(List<Field> fields){
        Field[] fieldsArray = new Field[fields.size()];

        for (int i = 0; i < fields.size(); i++) {
            fieldsArray[i] = fields.get(i);
        }
        return fieldsArray;
    }
}
