package ru.otus.l3Generics;

import ru.otus.l3Generics.utils.MyArrayList;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        ////run tests
        MyArrayList<String> myArrayList = new MyArrayList<>();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("q");
        arrayList.add("u");
        myArrayList.addAll(arrayList);

        myArrayList = new MyArrayList<>();
        myArrayList.add("one");
        myArrayList.add("two");
        myArrayList.add("three");
        ArrayList<String> dest = new ArrayList<>(3);
        dest.add("1");
        dest.add("2");
        dest.add("3");
        Collections.copy(dest, myArrayList);

        myArrayList = new MyArrayList<>();
        myArrayList.add("f");
        myArrayList.add("a");
        myArrayList.add("b");
        Collections.sort(myArrayList);
    }
}
