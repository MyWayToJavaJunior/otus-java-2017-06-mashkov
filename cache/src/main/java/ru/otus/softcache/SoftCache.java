package ru.otus.softcache;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;

public class SoftCache<K, V> implements SimpleCache<K,V>{
    private static final int TIME_THRESHOLD_MS = 1;
    private static final Logger LOGGER = Logger.getLogger(SoftCache.class.getName());

    int maxElements;
    long lifeTimeMs;
    long idleTimeMs;
    boolean isEternal;

    private final Map<K, CacheElement<K, V>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();
    private MemoryManager memoryManagement;

    private int missCount = 0;
    private int hitCount = 0;
    private int deadReference = 0;

    SoftCache(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs;
        this.idleTimeMs = idleTimeMs;
        this.isEternal = isEternal;
        setMemoryManagement(MemoryManagement.FIFO.manager);

        try {
            initMBen();
        } catch (MalformedObjectNameException | NotCompliantMBeanException | InstanceAlreadyExistsException | MBeanRegistrationException e) {
            e.printStackTrace();
        }
    }

    private void initMBen() throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        CacheController mBean = new CacheController(this);
        //Some mBean = new Some();
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=CacheController");
        mbs.registerMBean(mBean, name);
    }

    @Override
    public void put(K key, V element){
        if (elements.size() == maxElements) {
            K firstKey = (K) memoryManagement.getKey(elements);
            elements.remove(firstKey);
        }/*
        if (element.getValue()==null){
            elements.remove(key);
            return;
        }*/
        elements.put(key, new CacheElement<>(key, element));
        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            setIdleTimerTask(key);

        }
    }

    private void setIdleTimerTask(K key){
        if (idleTimeMs != 0) {
            TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
            timer.schedule(idleTimerTask, idleTimeMs);
        }
    }

    @Override
    public Optional<V> get(K key) {
        CacheElement<K, V> element = elements.get(key);
        Optional<V> result;
        if (element == null) {
            missCount++;
            result =  Optional.empty();
        } else if (element.get() == null){
            LOGGER.info("get empty weak reference");
            deadReference++;
            elements.remove(key);
            result = Optional.empty();
        } else {
            hitCount++;
            element.setAccessed();
            result = Optional.of(element.get());
        }
        return result;
    }

    @Override
    public int getHitCount() {
        return hitCount;
    }

    @Override
    public int getMissCount() {
        return missCount;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    @Override
    public int getDeadReference() {
        return deadReference;
    }

    public void setMemoryManagement(MemoryManager management){
        this.memoryManagement = management;
    }

    private TimerTask getTimerTask(final K key, Function<CacheElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheElement<K, V> checkedElement = elements.get(key);


                if (checkedElement == null ||
                        isT1BeforeT2(timeFunction.apply(checkedElement), System.currentTimeMillis())) {
                    elements.remove(key);
                } else if (idleTimeMs !=0){
                    setIdleTimerTask(key);
                }
            }
        };
    }


    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

}
