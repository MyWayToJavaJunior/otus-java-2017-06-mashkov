package ru.otus.threads;

import ru.otus.threads.pool.SimpleThreadPool;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.*;
import java.util.logging.Formatter;

/**
 * @autor slonikmak on 18.09.2017.
 * Сортировка с помощью своего пула потоков @{@link SimpleThreadPool}
 */
public class ArrayUtils {
    private static final Logger LOGGER = Logger.getLogger(ArrayUtils.class.getName());
    private static final int THREAD_SIZE = 4;

    private static ArrayUtils instance;
    private SimpleThreadPool threadPool;
    private Result<int[]> resultObj;

    private ArrayUtils() {
        threadPool = new SimpleThreadPool(THREAD_SIZE);
        resultObj = new Result<>();
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
        LOGGER.setLevel(Level.OFF);

    }

    static ArrayUtils getInstance() {
        if (instance == null) {
            instance = new ArrayUtils();
        }
        return instance;
    }

    public int[] sort(int[] array) throws InterruptedException {

        LOGGER.info("start sorting array: " + Arrays.toString(array));

        //Очередь с результатами сортировки отдельных кусков
        BlockingQueue<int[]> sortedQueue = new ArrayBlockingQueue<>(THREAD_SIZE);
        //размер куска массива для сортировки
        int chunkSize = (int) Math.ceil((float) array.length / THREAD_SIZE);

        //запус задач на сортировку
        for (int i = 0; i < THREAD_SIZE; i++) {
            int startIndex = i * chunkSize;
            int endIndex = startIndex + chunkSize;

            if (endIndex > array.length - 1) {
                endIndex = array.length;
            }

            int[] arrayToSort = Arrays.copyOfRange(array, startIndex, endIndex);

            threadPool.execute(() -> {
                LOGGER.info(Thread.currentThread().getName() + " start sort");
                Arrays.sort(arrayToSort);
                sortedQueue.add(arrayToSort);
                LOGGER.info(Thread.currentThread().getName() + " finish sort");
            });
        }

        //Запуск задач на слияние
        for (int i = 0; i < THREAD_SIZE; i++) {
            threadPool.execute(() -> {
                merge(sortedQueue, array.length, resultObj);
            });
        }


        //Ждём результатов
        while (!resultObj.done.get()) {

        }

        LOGGER.info("finish sorting");
        int[] result = resultObj.getValue();
        LOGGER.info("result " + Arrays.toString(result));
        return result;
    }


    private int[] merge(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int i = a.length - 1, j = b.length - 1, k = result.length;
        while (k > 0)
            result[--k] =
                    (j < 0 || (i >= 0 && a[i] >= b[j])) ? a[i--] : b[j--];
        return result;
    }


    //метод для многопоточного слияния работает с очередью из отсортированных кусков массива

    /**
     *
     * @param queue очередь из отсортированных кусков масси
     * @param size размер исходгного массива
     * @param resultObj объект для отправки результата
     */
    private void merge(BlockingQueue<int[]> queue, int size, Result resultObj) {
        LOGGER.info("start merging");
        while (true) {
            try {
                //берём кусок массива
                int[] sortedArr1 = queue.poll(50, TimeUnit.MILLISECONDS);
                if (sortedArr1 != null) {
                    //если длинна куска совпадает с длинной исходного массива
                    // то кладём его обратно и заканчиваем
                    if (sortedArr1.length == size) {
                        queue.put(sortedArr1);
                        LOGGER.info("stop merging " + Thread.currentThread().getName());
                        break;
                    }
                    //или берём второй кусок, если его нет(видимо он взят другим потоком)
                    //кладём первый кусок в очередь
                    int[] sortedArr2 = queue.poll(50, TimeUnit.MILLISECONDS);
                    if (sortedArr2 != null) {
                        queue.put(merge(sortedArr1, sortedArr2));
                    } else {
                        queue.put(sortedArr1);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int[] result;
        try {
            result = queue.take();
            //добавляем результат в объект результата и заканчиваем
            resultObj.setValue(result);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private class Result<T> {
        private T value;
        private AtomicBoolean done = new AtomicBoolean(false);

        void setValue(T value) {
            this.value = value;
            done.set(true);
        }

        T getValue() {
            return value;
        }
    }

}
