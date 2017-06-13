package ru.otus;


import sun.rmi.runtime.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        int elements = 10000000;
        //runTest(emptyStringSupplier, 100000);
        runTest(fullStringSupplier, elements);
        //runTest(integerSupplier, 100000);
        //runTest(emptyIntArraySupplier, 1000000);
        //runTest(emptyIntArraySupplier, 10000);
    }

    static void runTest(Supplier supplier, int elements){
        String className = supplier.get().getClass().getCanonicalName();
        System.gc();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runtime runtime = Runtime.getRuntime();
        long startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Memory before: "+startMem);
        Object[] result = new Object[elements];
        for (int i = 0; i < elements; i++) {
            result[i] = supplier.get();
        }
        long stopMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long diffMemory = stopMem - startMem;
        System.out.println("Memory after: "+stopMem);
        System.out.println("Test for "+className);
        System.out.println("Used memory is: "+diffMemory);
        System.out.println("Objects created: "+result.length);
        System.out.println("Memory per object: "+diffMemory/elements);

    }



    static Supplier<String> emptyStringSupplier = String::new;
    static Supplier<String> fullStringSupplier = ()->"qwertt";
    static Supplier<Object> emptyObjectSupplier = Object::new;
    static Supplier<Person> personSupplier = ()->new Person("john", 30);
    static Supplier<int[]> emptyIntArraySupplier = () -> new int[0];
    static Supplier<Integer> integerSupplier = ()-> 5;

    public static class Person{
        String name;
        int age;

        Person(){
        }

        Person(String name, int age){
            this.name = name;
            this.age = age;
        }
    }
}
