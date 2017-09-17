package ru.otus.softcache;


import java.util.Optional;

public interface SimpleCache<K, V>{

    void put(K key, V element);

    Optional<V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();

    int getDeadReference();

    void setMemoryManagement(MemoryManager management);
}
