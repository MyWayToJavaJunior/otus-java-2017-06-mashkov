package ru.otus.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @autor slonikmak on 19.09.2017.
 * Сортировка с помощью ForkJoin
 */
public class ForkJoinArraySort {
    private static final int THREAD_SIZE = 4;

    public static int[] sort(int[] arr){
        ArrayBlockingQueue<int[]> queue = new ArrayBlockingQueue<>(THREAD_SIZE);
        ForkJoinTask.invokeAll(createSortSubTask(arr))
                .stream()
                .map(ForkJoinTask::join)
                .collect(Collectors.toCollection(()->queue));

        ForkJoinTask.invokeAll(createMergeTasks(queue, arr.length));

        return queue.poll();
    }

    private static Collection<ParallelSortTask> createSortSubTask(int[] array){
        int chunkSize = (int) Math.ceil((float) array.length / THREAD_SIZE);
        List<ParallelSortTask> tasks = new ArrayList<>();
        for (int i = 0; i < THREAD_SIZE; i++) {
            int startIndex = i * chunkSize;
            int endIndex = startIndex + chunkSize;

            if (endIndex > array.length - 1) {
                endIndex = array.length;
            }

            int[] arrayToSort = Arrays.copyOfRange(array, startIndex, endIndex);
            tasks.add(new ParallelSortTask(arrayToSort));
        }
        return tasks;
    }

    private static Collection<ParallelMergeTask> createMergeTasks(BlockingQueue<int[]> queue, int size){
        List<ParallelMergeTask> tasks = new ArrayList<>();
        for (int i = 0; i < THREAD_SIZE / 2; i++) {
            tasks.add(new ParallelMergeTask(queue, size));
        }

        return tasks;

    }

    private static class ParallelSortTask extends RecursiveTask<int[]> {
        private int[] arr;

        ParallelSortTask(int[] arr) {
            this.arr = arr;
        }
        @Override
        protected int[] compute() {
            Arrays.sort(arr);
            return arr;
        }
    }

    private static class ParallelMergeTask extends RecursiveAction{

        BlockingQueue<int[]> queue;
        int size;

        ParallelMergeTask(BlockingQueue<int[]> queue, int size){
            this.queue = queue;
            this.size = size;
        }

        @Override
        protected void compute() {
            try {
                int[] sortedArr1 = queue.poll(50, TimeUnit.MILLISECONDS);
                if (sortedArr1 != null) {
                    if (sortedArr1.length == size) {
                        queue.put(sortedArr1);
                        return;
                    }

                    int[] sortedArr2 = queue.poll(50, TimeUnit.MILLISECONDS);
                    if (sortedArr2 != null) {
                        queue.put(merge(sortedArr1, sortedArr2));
                    } else {
                        queue.put(sortedArr1);
                    }
                    ForkJoinTask.invokeAll(new ParallelMergeTask(queue, size));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static int[] merge(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int i = a.length - 1, j = b.length - 1, k = result.length;
        while (k > 0)
            result[--k] =
                    (j < 0 || (i >= 0 && a[i] >= b[j])) ? a[i--] : b[j--];
        return result;
    }


}
