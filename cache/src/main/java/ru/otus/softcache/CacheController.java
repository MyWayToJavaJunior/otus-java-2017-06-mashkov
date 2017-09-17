package ru.otus.softcache;

/**
 * @autor slonikmak on 11.09.2017.
 */
public class CacheController implements CacheControllerMBean {
    SoftCache cache;

    public CacheController(SoftCache cache){
        this.cache = cache;
    }

    @Override
    public int getHitCount() {
        return cache.getHitCount();
    }

    @Override
    public int getMissCount() {
        return cache.getMissCount();
    }

    @Override
    public int getDeadReference() {
        return cache.getDeadReference();
    }

    @Override
    public int getMaxElement() {
        return cache.maxElements;
    }

    @Override
    public long getLifeTime() {
        return cache.lifeTimeMs;
    }

    @Override
    public long getIdleTime() {
        return cache.idleTimeMs;
    }

    @Override
    public boolean isEternal() {
        return cache.isEternal;
    }

    @Override
    public void setLifeTime(long lifeTime) {
        cache.lifeTimeMs = lifeTime;
    }

    @Override
    public void setIdleTime(long idleTime) {
        cache.idleTimeMs = idleTime;
    }
}
