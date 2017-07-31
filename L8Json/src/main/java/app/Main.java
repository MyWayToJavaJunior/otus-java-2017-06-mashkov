package app;


import jsonParser.JsonParser;
import ru.otus.testFramework.ReflectionHelper;
import ru.otus.tests.SecondTestClass;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.lang.reflect.Field;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        JsonParser parser = new JsonParser();

        System.out.println(parser.toJson(new SecondTest()));

        /*Stack stack = new Stack();
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
        });*/


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

    public static class SecondTest{
        private String str = "2";
        private int i = 1;
        private String str2 = "3";
        private float f = 1.3f;
    }
}
