package ru.otus.threads.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

/**
 * @autor slonikmak on 19.09.2017.
 */
public class SimpleThreadPool {
    private static final Logger LOGGER = Logger.getLogger(SimpleThreadPool.class.getName());

    private final int nThreads;
    private final PoolItem[] threads;
    private final BlockingQueue<Runnable> queue;
    private boolean stop = false;

    public SimpleThreadPool(int nThreads)
    {
        this.nThreads = nThreads;
        queue = new LinkedBlockingQueue<>();
        threads = new PoolItem[nThreads];

        for (int i=0; i<nThreads; i++) {
            threads[i] = new PoolItem();
            threads[i].setName("Thread"+i);
            threads[i].start();
        }
        LOGGER.setUseParentHandlers(false);

        Handler handler = new ConsoleHandler();
        handler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return record.getLoggerName()+" "
                        +record.getMillis()+" "
                        +record.getLevel()+" "
                        +record.getMessage()+"\n";
            }
        });
        LOGGER.addHandler(handler);
    }

    public void execute(Runnable r) {
        try {
            queue.put(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //FIXME: не понятно как дожидаться окончания задач
    public void shutDown(){
        for (PoolItem worker :
                threads) {
            LOGGER.info("try to stop "+worker.getName());
            worker.stopThread();
            //worker.interrupt();
        }
    }


    private class PoolItem extends Thread {
        public void run() {
            while (!stop || Thread.currentThread().isInterrupted()) {
                try {
                    Runnable runnable = queue.poll(50, TimeUnit.MILLISECONDS);
                    if (runnable!=null){
                        runnable.run();
                    }
                    //queue.take().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            LOGGER.info(getName()+ " is finished");
        }

        private void stopThread(){
            stop = true;
        }

    }


}
