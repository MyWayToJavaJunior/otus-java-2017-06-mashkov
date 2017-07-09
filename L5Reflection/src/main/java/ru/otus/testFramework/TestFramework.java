package ru.otus.testFramework;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestFramework {
    private TestFramework(){}

    public static void runTests(List<Class<?>> classes){
        classes.forEach(TestFramework::runOneTestClass);
    }

    public static void runTests(Class<?> ...classes){
        for (int i = 0; i < classes.length; i++) {
            runOneTestClass(classes[i]);
        }
    }

    public static void runTests(String packageName){
        runTests(PackageScanner.find(packageName));
    }

    private static<T> void runOneTestClass(Class<T> clazz){
        Method[] methods = ReflectionHelper.getMethods(clazz);
        ArrayList<Method> beforeMethods = new ArrayList<>();
        ArrayList<Method> afterMethods = new ArrayList<>();
        ArrayList<Method> testMethods = new ArrayList<>();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
                continue;
            }
            if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
                continue;
            }
            if (method.isAnnotationPresent(Test.class)) testMethods.add(method);
        }

        testMethods.forEach(testMethod->{
            T obj = ReflectionHelper.instantiate(clazz);
            beforeMethods.forEach(beforeMethod-> ReflectionHelper.callMethod(obj, beforeMethod.getName()));
            ReflectionHelper.callMethod(obj, testMethod.getName());
            afterMethods.forEach(afterMethod->ReflectionHelper.callMethod(obj, afterMethod.getName()));
        });
    }


}
