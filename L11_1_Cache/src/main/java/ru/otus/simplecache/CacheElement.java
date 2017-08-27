package ru.otus.simplecache;

import java.lang.ref.WeakReference;

public class CacheElement<V> extends WeakReference<V> {

    private final long creationTime;

    public CacheElement(V referent) {
        super(referent);
        creationTime = System.nanoTime();
    }

    public long getCreationTime(){
        return creationTime;
    }

    public void setAccessed(){

    }

}
