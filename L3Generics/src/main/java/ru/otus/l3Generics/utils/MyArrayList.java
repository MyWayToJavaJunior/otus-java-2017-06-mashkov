package ru.otus.l3Generics.utils;

import java.util.*;

public class MyArrayList<T> implements List<T> {

    private Object[] elements;
    private final int INIT_ARRAY_SIZE = 10;
    private int size = 0;

    public MyArrayList(){
        elements = new Object[INIT_ARRAY_SIZE];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public boolean contains(Object o) {
        return false;
    }

    public Iterator<T> iterator() {
        return null;
    }

    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    public boolean add(T t) {
        if (size%INIT_ARRAY_SIZE == 0) extendArray();
        elements[size] = t;
        size++;
        return true;
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public boolean addAll(Collection<? extends T> c) {
        c.forEach(this::add);
        return false;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public void clear() {

    }

    public T get(int index) {
        return (T) elements[index];
    }

    public T set(int index, T element) {
        return null;
    }

    public void add(int index, T element) {

    }

    public T remove(int index) {
        return null;
    }

    public int indexOf(Object o) {
        return 0;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public ListIterator<T> listIterator() {
        return new ListIterator<T>() {
            private int currentPosition = 0;
            @Override
            public boolean hasNext() {
                return currentPosition<size-1;
            }

            @Override
            public T next() {
                return (T) elements[currentPosition++];
            }

            @Override
            public boolean hasPrevious() {
                return currentPosition!=0;
            }

            @Override
            public T previous() {
                return (T) elements[--currentPosition];
            }

            @Override
            public int nextIndex() {
                return currentPosition+1;
            }

            @Override
            public int previousIndex() {
                return currentPosition-1;
            }

            @Override
            public void remove() {

            }

            @Override
            public void set(T t) {

            }

            @Override
            public void add(T t) {

            }
        };
    }

    public ListIterator<T> listIterator(int index) {
        return null;
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    private void extendArray(){
        int newSize =size+INIT_ARRAY_SIZE;
        Object[] newArr = Arrays.copyOf(elements, newSize);
        elements = newArr;
    }
}
