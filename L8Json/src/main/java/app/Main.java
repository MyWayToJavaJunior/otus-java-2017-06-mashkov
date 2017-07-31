package app;


import ru.otus.testFramework.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Stack stack = new Stack();
        System.out.println(stack.getClass().getSuperclass().getSuperclass());

        int i = 5;
        //System.out.println(typeof(i));
        TestClass testClass = new TestClass();

        Field[] fields = ReflectionHelper.getFields(testClass);
        Arrays.asList(fields).forEach(f->{
            System.out.println("name "+f.getName());
            System.out.println("Class " + f.getType());
            System.out.println("Parent "+ReflectionHelper.getFieldValue(testClass, f.getName()).getClass().getSuperclass());
            System.out.println("From field: "+ReflectionHelper.getFieldValue(testClass, f.getName()).getClass());
            System.out.println("equals "+ AbstractMap.class.equals(ReflectionHelper.getFieldValue(testClass, f.getName()).getClass().getSuperclass()));
        });


    }

    public static Class<Integer> typeof(final int expr) {
        return Integer.TYPE;
    }

    public static Class<Long> typeof(final long expr) {
        return Long.TYPE;
    }

    public static class SubClass{
        String string = "sub";
        int i = 1;
    }

    public static class TestClass{
        String str = "eee";
        int i = 5;
        Integer integer = 8;
        SubClass subClass = new SubClass();
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("q","e"));
        HashMap<Integer, String> map = new HashMap<>();
        int[] arr = new int[]{1,2,3};

        public TestClass(){
            map.put(2,"2");
            map.put(3,"3");
        }
    }
}
