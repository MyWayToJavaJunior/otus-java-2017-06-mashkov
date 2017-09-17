package ru.otus.softcache;

/**
 * @autor slonikmak on 11.09.2017.
 */
public interface CacheControllerMBean {
    int getHitCount();
    int getMissCount();
    int getDeadReference();
    int getMaxElement();
    long getLifeTime();
    long getIdleTime();
    boolean isEternal();
    void setLifeTime(long lifeTime);
    void setIdleTime(long idleTime);
}
