import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        installGCMonitoring();

        int size = 1000;

        ArrayList<MyReference<BigObj>> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            list.add(new MyReference<>(new BigObj()));
        }


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.gc();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.gc();

        int count = 0;

        for (int i = 0; i < size; i++) {
            if (list.get(i).get()!=null) count++;
        }

        System.out.println(count);

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

                    System.out.println(gctype+": - "+info.getGcInfo().getId()+
                            ", "+info.getGcName()+"(from "+info.getGcCause()+") "+duration+" millis");


                }
            };

            emitter.addNotificationListener(listener, null, null);
        }
    }

    static class BigObj{
        private long[] arr;
        BigObj(){
            arr = new long[1000*100];
        }
    }

    static class MyReference<T> extends WeakReference<T>{

        public MyReference(T referent) {
            super(referent);
        }
    }
}
