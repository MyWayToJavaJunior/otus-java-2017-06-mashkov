package ru.otus.simplecache.cache;

import java.util.*;
import java.util.function.Function;

/**
 * Created by tully.
 */
public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {
    private static final int TIME_THRESHOLD_MS = 1;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, MyElement<K, V>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();
    private MemoryManager memoryManagement;

    private int hit = 0;
    private int miss = 0;

    public CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
        memoryManagement = MemoryManagement.FIFO.manager;
    }

    public void put(MyElement<K, V> element) {
        if (elements.size() == maxElements) {
            K firstKey = (K) memoryManagement.getKey(elements);
            elements.remove(firstKey);
        }

        K key = element.getKey();
        if (element.getValue()==null){
            elements.remove(key);
            return;
        }
        elements.put(key, element);

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs);
            }
        }
    }

    public MyElement<K, V> get(K key) {
        MyElement<K, V> element = elements.get(key);
        if (element != null) {
            hit++;
            element.setAccessed();
        } else {
            miss++;
        }
        return element;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<MyElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                MyElement<K, V> checkedElement = elements.get(key);
                if (checkedElement == null ||
                        isT1BeforeT2(timeFunction.apply(checkedElement), System.currentTimeMillis())) {
                    elements.remove(key);
                }
            }
        };
    }


    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

    public void setMemoryManagement(MemoryManager management){
        this.memoryManagement = management;
    }


}
