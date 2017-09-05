package ru.otus.simplecache;

import ru.otus.simplecache.cache.CacheEngineImpl;
import ru.otus.simplecache.cache.MemoryManager;
import ru.otus.simplecache.cache.MyElement;

import java.lang.ref.SoftReference;
import java.util.Optional;

public class SimpleCacheEngine<K, V> implements SimpleCache<K,V>{

    private CacheEngineImpl<K, SoftReference<V>> cacheEngine;

    private int missCount = 0;

    public SimpleCacheEngine(){

    }

    SimpleCacheEngine(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        cacheEngine = new CacheEngineImpl<>(maxElements, lifeTimeMs, idleTimeMs, isEternal);
    }

    @Override
    public void put(K key, V element){
        cacheEngine.put(new MyElement<>(key, new SoftReference<V>(element)));
    }



    @Override
    public Optional<V> get(K key) {
        MyElement<K, SoftReference<V>> element = cacheEngine.get(key);
        if (element == null) return Optional.empty();
        V result = element.getValue().get();
        if (result == null){
            cacheEngine.put(new MyElement<>(key, null));
            missCount++;
            return Optional.empty();
        }
        return Optional.of(result);
    }

    @Override
    public int getHitCount() {
        return cacheEngine.getHitCount()-missCount;
    }

    @Override
    public int getMissCount() {
        return cacheEngine.getMissCount()+missCount;
    }

    @Override
    public void dispose() {
        cacheEngine.dispose();
    }

    public int getDeadReferences(){
        return  missCount;
    }



    void setMemoryManagement(MemoryManager management){
        cacheEngine.setMemoryManagement(management);
    }

}
