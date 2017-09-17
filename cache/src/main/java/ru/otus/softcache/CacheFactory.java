package ru.otus.softcache;

/**
 * @autor slonikmak on 10.09.2017.
 */
public class CacheFactory {
    public static <K,V> SoftCache<K,V> getEternalCache(int maxElement){
        return new SoftCache<>(maxElement, 0, 0, true);
    }
    public static <K,V> SoftCache<K,V> getLifeCache(int maxElement, long lifeTime){
        return new SoftCache<>(maxElement, lifeTime, 0, false);
    }
    public static <K,V> SoftCache<K,V> getIdleCache(int maxElement, long idleTime){
        return new SoftCache<>(maxElement, 0, idleTime, false);
    }

}
