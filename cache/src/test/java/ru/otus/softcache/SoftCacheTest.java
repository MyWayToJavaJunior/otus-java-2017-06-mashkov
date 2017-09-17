package ru.otus.softcache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @autor slonikmak on 10.09.2017.
 */
class SoftCacheTest {

    @Test
    void maxElemTest(){
        SoftCache<Integer, TestObj> cache = new SoftCache<>(3, 0,0,true);

        TestObj obj = new TestObj("0");

        cache.put(0, obj);

        for (int i = 1; i < 4; i++) {
            cache.put(i, new TestObj(String.valueOf(i)));
        }

        assertFalse(cache.get(0).isPresent());


    }

    @Test
    void lifeTimeTest(){
        SoftCache<Integer, TestObj> cache = CacheFactory.getLifeCache(3, 100);
        for (int i = 0; i < 3; i++) {
            cache.put(i, new TestObj(String.valueOf(i)));
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertFalse(cache.get(0).isPresent());
    }

    @Test
    void idleTimeTest(){
        SoftCache<Integer, TestObj> cache = CacheFactory.getIdleCache(3, 100);
        for (int i = 0; i < 3; i++) {
            cache.put(i, new TestObj(String.valueOf(i)));
        }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(cache.get(0).isPresent());

        try {
            Thread.sleep(70);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(cache.get(0).isPresent());
        assertFalse(cache.get(1).isPresent());

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(cache.get(0).isPresent());

    }

    @Test
    void weakRefTest(){
        int size = 1000;

        SoftCache<Integer, BigObj> cache = CacheFactory.getEternalCache(size);

        for (int i = 0; i < size; i++) {
            cache.put(i, new BigObj(i));
        }

        for (int i = 0; i < size; i++) {
            cache.get(i);
        }

        System.out.println("hit "+cache.getHitCount());
        System.out.println("miss "+cache.getMissCount());
        System.out.println("dead "+cache.getDeadReference());

        assertFalse(cache.get(0).isPresent());
    }


    static class TestObj{
        String name;

        TestObj(String name){
            this.name = name;
        }
    }

    static class BigObj{
        int num;
        int[] arr = new int[1000*1000];

        BigObj(int num){
            this.num = num;
        }
    }
}