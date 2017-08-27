package ru.otus.simplecache;

import ru.otus.cache.MemoryManager;

public class CacheBuilder<K,V> {
    private SimpleCacheEngine<K,V> cacheEngine;

    public CacheBuilder(){

    }

    public  CacheBuilder<K,V> getEternalCache(int maxElement){
        cacheEngine = new SimpleCacheEngine(maxElement, 0,0, true);
        return this;
    }

    public  CacheBuilder<K,V> getLiveCache(int maxElement, long lifeTimeMillis){
        cacheEngine = new SimpleCacheEngine(maxElement, lifeTimeMillis,0, false);
        return this;
    }

    public  CacheBuilder<K,V> getIdleCache(int maxElement, long idleTimeMillis){
        cacheEngine = new SimpleCacheEngine(maxElement, 0,idleTimeMillis, false);
        return this;
    }

    public  CacheBuilder<K,V> getCache(int maxElement, long lifeTimeMillis, long idleTimeMillis, boolean isEternal){
        cacheEngine = new SimpleCacheEngine(maxElement, lifeTimeMillis,idleTimeMillis, isEternal);
        return this;
    }

    public CacheBuilder<K,V> setExtractor(SimpleCacheEngine.KeyExtractor<K,V> extractor){
        cacheEngine.setExtractor(extractor);
        return this;
    }

    public void setMemoryManagement(MemoryManager memoryManagement){
        cacheEngine.setMemoryManagement(memoryManagement);
    }

    public SimpleCacheEngine<K,V> build(){
        return cacheEngine;
    }
}
