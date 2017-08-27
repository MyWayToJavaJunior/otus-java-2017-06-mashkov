package ru.otus.cache;

import java.util.Map;

public interface MemoryManager<K, V> {
        K getKey(Map<K,V> elements);

}
