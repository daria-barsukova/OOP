package ru.nsu.barsukova;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Tests {

    @Test
    void test1() {
        int[] arr = Heapsort.sort(new int[]{});
        assertArrayEquals(arr, new int[]{});
    }

    @Test
    void test2() {
        int[] arr = Heapsort.sort(new int[]{5, 4, 3, 2, 1});
        assertArrayEquals(arr, new int[]{1, 2, 3, 4, 5});
    }

    @Test
    void test3() {
        int[] arr = new int[10000000];
        for (int i = 0; i < 10000000; i++) {
            arr[i] = (int) (Math.random() * 10000000);
        }
        Heapsort.sort(arr);
        for (int i = 0; i < 9999999; i++) {
            if (arr[i] > arr[i + 1]) {
                fail();
            }
        }
    }
}
