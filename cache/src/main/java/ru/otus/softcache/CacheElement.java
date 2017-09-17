package ru.otus.softcache;

import java.lang.ref.SoftReference;

/**
 * Created by tully.
 */
@SuppressWarnings("WeakerAccess")
public class CacheElement<K, V> extends SoftReference<V>{
    private final K key;
    private final long creationTime;
    private long lastAccessTime;

    public CacheElement(K key, V value) {
        super(value);
        this.key = key;
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }
}
