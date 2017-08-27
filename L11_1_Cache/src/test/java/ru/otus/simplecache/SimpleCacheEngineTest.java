package ru.otus.simplecache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class SimpleCacheEngineTest {



    @BeforeEach
    void setUp(){
    }

    @AfterEach
    void tearDown(){

    }

    @Test
    void putMaxElem() {
        SimpleCacheEngine<Integer, TestObj> cache = new CacheBuilder<Integer, TestObj>()
                .getEternalCache(3).build();
        int size = 10;
        for (int i = 0; i < size; i++) {
            cache.put(i, new TestObj(i, "name"+i));
        }

        cache.get(0);
        cache.get(9);
        Assertions.assertEquals(1, cache.getMissCount());
        Assertions.assertEquals(1, cache.getHitCount());


        //System.out.println(cacheEngine.get(3).isPresent());
    }

    @Test
    void putLiveTime(){
        SimpleCacheEngine<Integer, TestObj> cache = new CacheBuilder<Integer, TestObj>()
                .getLiveCache(3, 100).build();
        int size = 10;
        for (int i = 0; i < size; i++) {
            cache.put(i, new TestObj(i, "name"+i));
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cache.get(1);
        cache.get(9);
        Assertions.assertEquals(2, cache.getMissCount());
        //Assertions.assertEquals(1, cache.getHitCount());

    }

    @Test
    void putIdleTime(){
        SimpleCacheEngine<Integer, TestObj> cache = new CacheBuilder<Integer, TestObj>()
                .getIdleCache(3, 200).build();
        int size = 3;
        for (int i = 0; i < size; i++) {
            cache.put(i, new TestObj(i, "name"+i));
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cache.get(0);
        cache.get(1);
        Assertions.assertEquals(2, cache.getHitCount());
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cache.get(0);
        cache.get(1);
        cache.get(2);
        Assertions.assertEquals(4, cache.getHitCount());
        Assertions.assertEquals(1, cache.getMissCount());

    }

    @Test
    void weakRefTest(){
        int max = 1000;

        SimpleCacheEngine<Integer, BigObj> cache = new CacheBuilder<Integer, BigObj>()
                .getEternalCache(max).build();

        for (int i = 0; i < max; i++) {
            cache.put(i, new BigObj());
        }

        System.gc();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < max; i++) {
            cache.get(i);
        }

        Assertions.assertTrue(max>cache.getHitCount());



    }
    @Test
    void extractorTest(){
        TestObj testObj = new TestObj(1,"Bill");
        SimpleCacheEngine<String, TestObj> cache = new CacheBuilder<String, TestObj>()
                .getEternalCache(3)
                .setExtractor(o->o.name)
                .build();

        try {
            cache.put(testObj);
        } catch (CacheException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(testObj, cache.get(testObj.name).orElse(null));
    }

    static class TestObj{
        int id;
        String name;

        TestObj(int id, String name){
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestObj)) return false;

            TestObj testObj = (TestObj) o;

            if (id != testObj.id) return false;
            return name != null ? name.equals(testObj.name) : testObj.name == null;
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "TestObj{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    static class BigObj{
        long[] longs;

        BigObj(){
            longs = new long[1000*1000];
        }
    }



}