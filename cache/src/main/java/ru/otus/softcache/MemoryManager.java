package ru.otus.softcache;

import java.util.Map;

public interface MemoryManager<K, V> {
        K getKey(Map<K, V> elements);
}
