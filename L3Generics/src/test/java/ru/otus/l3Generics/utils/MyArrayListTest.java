package ru.otus.l3Generics.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayListTest {

    MyArrayList<String> myArrayList;

    @BeforeEach
    public void setup(){
        myArrayList = new MyArrayList<>();
    }

    @Test
    public void addTest(){
        myArrayList.add("new String");
        assertEquals(myArrayList.size(), 1);
    }

    @Test
    public void addAllTest(){
        ArrayList<String> list = new ArrayList<>();
        list.add("odin");
        list.add("dva");
        list.add("tri");

        myArrayList.addAll(list);

        assertEquals(list.size(), myArrayList.size());

    }

    @Test
    public void copyTest(){
        myArrayList.add("one");
        myArrayList.add("two");
        myArrayList.add("three");
        ArrayList<String> dest = new ArrayList<>(3);
        dest.add("1");
        dest.add("2");
        dest.add("3");
        Collections.copy(dest, myArrayList);
        assertArrayEquals(myArrayList.toArray(), dest.toArray());

    }

    @Test
    public void sortTest(){
        myArrayList.add("f");
        myArrayList.add("a");
        myArrayList.add("b");

        ArrayList<String> list = new ArrayList<>();
        list.add("f");
        list.add("a");
        list.add("b");
        Collections.sort(myArrayList);
        Collections.sort(list);

        assertArrayEquals(list.toArray(), myArrayList.toArray());
    }

}