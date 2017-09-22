package ru.otus.threads;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * @autor slonikmak on 21.09.2017.
 */
public class SimpleForkJoinSort {

    public static int[] sort(int[] array){
        return new ForkJoinPool().invoke(new SortTask(array, array.length));
    }

    private static class SortTask extends RecursiveTask<int[]>{

        static final int THREAD_THRESHOLD = 2000;

        int[] array;
        int size;

        SortTask(int[] array, int size){
            this.array = array;
            this.size = size;
        }


        @Override
        protected int[] compute() {
            if (array.length<=THREAD_THRESHOLD){
                //System.out.println("sort "+Arrays.toString(array));
                Arrays.sort(array);
                return array;
            } else {
                //System.out.println(array.length);
                SortTask task1 = new SortTask(Arrays.copyOfRange(array, 0, (array.length-1)/2+1), size);
                SortTask task2 = new SortTask(Arrays.copyOfRange(array, (array.length-1)/2+2, (array.length)), size);

                task1.fork();
                task2.fork();
                int[] result1 = task1.join();
                int[] result2 = task2.join();
                return merge(result1, result2);
            }
        }

        private int[] merge(int[] a, int[] b) {
            int[] result = new int[a.length + b.length];
            int i = a.length - 1, j = b.length - 1, k = result.length;
            while (k > 0)
                result[--k] =
                        (j < 0 || (i >= 0 && a[i] >= b[j])) ? a[i--] : b[j--];
            return result;
        }

        public static void main(String[] args) {
            int[] arr = new int[]{5,2,6,4,3,9,7,2,5,6,0};
            int[] result = new ForkJoinPool().invoke(new SortTask(arr, arr.length));
            System.out.println(Arrays.toString(result));
        }


    }

}
