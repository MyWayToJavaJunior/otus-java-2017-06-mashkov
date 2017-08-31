package ru.otus.simplecache;


public class CacheFactory<K,V> {


    public SimpleCacheEngine<K,V> getEternalCache(int maxElement){
        return  new SimpleCacheEngine(maxElement, 0,0, true);
    }

    public SimpleCacheEngine<K,V> getLiveCache(int maxElement, long lifeTimeMillis){
        return new SimpleCacheEngine(maxElement, lifeTimeMillis,0, false);
    }

    public SimpleCacheEngine<K,V> getIdleCache(int maxElement, long idleTimeMillis){
        return new SimpleCacheEngine(maxElement, 0,idleTimeMillis, false);
    }

    public SimpleCacheEngine<K,V> getCache(int maxElement, long lifeTimeMillis, long idleTimeMillis, boolean isEternal){
        return new SimpleCacheEngine(maxElement, lifeTimeMillis,idleTimeMillis, isEternal);
    }


}
