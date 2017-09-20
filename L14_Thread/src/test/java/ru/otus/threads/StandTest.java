package ru.otus.threads;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * @autor slonikmak on 20.09.2017.
 */
public class StandTest {
    @Test
    void stand(){
        Random random = new Random();

        //при size=1500000 результаты forkJoin и Arrays.parallelSort приближаются и становяться
        //лучше чем у Arrays.sort
        int size = 1500000;
        int[] sourceArray = new int[size];
        int[] sourceArray2 = new int[size];
        int[] actualArrayUtils = new int[size];
        int[] actualForkJoin;
        for (int i = 0; i < size; i++) {
            sourceArray[i] = random.nextInt();
            sourceArray2[i] = random.nextInt();
        }
        long startArrayUtilsTime = System.nanoTime();
        try {
            actualArrayUtils = ArrayUtils.getInstance().sort(sourceArray);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long stopArrayUtilsTime = System.nanoTime();
        long startForkJoinTime = System.nanoTime();
        actualForkJoin = ForkJoinArraySort.sort(sourceArray);
        long stopForkJoinTime = System.nanoTime();
        long startReferenceTime = System.nanoTime();
        Arrays.sort(sourceArray);
        long stopReferenceTime = System.nanoTime();
        long startReferenceParalllTime = System.nanoTime();
        Arrays.parallelSort(sourceArray2);
        long stopReferenceParallTime = System.nanoTime();

        System.out.println("ArrayUtils: "+(stopArrayUtilsTime-startArrayUtilsTime));
        System.out.println("ForkJoin:   "+(stopForkJoinTime-startForkJoinTime));
        System.out.println("Reference:  "+(stopReferenceTime-startReferenceTime));
        System.out.println("ParallelRef:"+(stopReferenceParallTime-startReferenceParalllTime));

        Assert.assertArrayEquals(sourceArray, actualArrayUtils);
        Assert.assertArrayEquals(sourceArray, actualForkJoin);


    }
}
