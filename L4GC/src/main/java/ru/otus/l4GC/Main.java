package ru.otus.l4GC;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void run() throws InterruptedException {
        installGCMonitoring();
        while (true){
            int size = 100000;
            String[] arr = new String[size];
            for (int i = 0; i < size; i++) {
                arr[i] = ""+i;
            }
            System.out.println(getString()+size);
            Thread.sleep(500);

        }


    }

    private static void installGCMonitoring() {
        List<GarbageCollectorMXBean> beans = ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean gcbean: beans){
            NotificationEmitter emitter = (NotificationEmitter) gcbean;

            System.out.println(gcbean.getName());

            NotificationListener listener = (notification, handback)->{
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)){
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                    long duration = info.getGcInfo().getDuration();
                    String gctype = info.getGcAction();

                    System.out.println(gctype+": - "+info.getGcInfo().getId()+", "+info.getGcName()+"(from "+info.getGcCause()+") "+duration+" millis");


                }
            };

            emitter.addNotificationListener(listener, null, null);
        }
    }

    public static String getString(){
        return "iteration, size = ";
    }
}
