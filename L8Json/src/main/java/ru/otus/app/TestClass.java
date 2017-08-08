package ru.otus.app;

import java.util.*;

public class TestClass {
    int i= 1;
    boolean b =true;
    String s = "2";
    InnerTestClass o;
    ArrayList<String> list;
    HashMap<String, Long> map;
    Queue<String> queue;
    InnerTestClass[] array;

    public TestClass(){
        list = new ArrayList<>(Arrays.asList("qwe","www"));
        o = new InnerTestClass();
        map = new HashMap<>();
        map.put("1", 23L);
        map.put("2",32L);
        queue = new PriorityQueue<>();
        queue.add("qw");
        queue.add("qa");
        array = new InnerTestClass[]{new InnerTestClass(),new InnerTestClass()};
    }
}
