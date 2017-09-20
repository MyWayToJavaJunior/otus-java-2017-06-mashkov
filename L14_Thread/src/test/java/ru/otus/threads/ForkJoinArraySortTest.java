package ru.otus.threads;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @autor slonikmak on 20.09.2017.
 */
class ForkJoinArraySortTest {
    @Test
    void forkJoinTest(){
        int[] arr = new int[]{12,13,0,1,3,5,4,9,8,6,7,2,10};
        int[] expected = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13};

        int[] actual = ForkJoinArraySort.sort(arr);

        assertArrayEquals(expected, actual);
    }

}