package ru.otus.simplecache;

import ru.otus.cache.CacheEngineImpl;
import ru.otus.cache.MemoryManager;
import ru.otus.cache.MyElement;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Optional;

public class SimpleCacheEngine<K, V> implements SimpleCache<K,V>{

    private CacheEngineImpl<K, SoftReference<V>> cacheEngine;

    private KeyExtractor<K,V> extractor;

    private int missCount = 0;

    public SimpleCacheEngine(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        cacheEngine = new CacheEngineImpl<>(maxElements, lifeTimeMs, idleTimeMs, isEternal);
        extractor = defaultExtractor;
    }

    public void setExtractor(KeyExtractor<K,V> extractor){
        this.extractor = extractor;
    }

    @Override
    public void put(V element) throws CacheException {
        cacheEngine.put(new MyElement<>(extractor.extract(element), new SoftReference<>(element)));
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

    void setMemoryManagement(MemoryManager management){
        cacheEngine.setMemoryManagement(management);
    }

    private KeyExtractor<K, V> defaultExtractor = element -> {
        throw new CacheException("Missing extractor");
    };

    public interface KeyExtractor<K, V>{
        K extract(V element) throws CacheException;
    }
}
