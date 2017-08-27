package ru.otus.simplecache;


import java.util.Optional;

public interface SimpleCache<K, V>{
    void put(V element) throws CacheException;

    void put(K key, V element);

    Optional<V> get(K key);


    int getHitCount();

    int getMissCount();

    void dispose();
}
