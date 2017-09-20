package ru.otus.threads;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @autor slonikmak on 18.09.2017.
 */
class ArrayUtilsTest {
    @Test
    void sort() {
        ArrayUtils utils = ArrayUtils.getInstance();

        int[] arr = new int[]{12,13,0,1,3,5,4,9,8,6,7,2,10};
        int[] expected = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13};
        try {
            assertArrayEquals(expected, utils.sort(arr));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}