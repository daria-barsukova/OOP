package dbarsukova;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * task-2-1-1 test.
 */

class SearchTests {

    @Test
    public void test1() {
        Assertions.assertFalse(new Sequence().isPrime(-1));
        assertTrue(new Sequence().isPrime(9679));
        assertFalse(new Sequence().isPrime(9680));
        assertThrows(NullPointerException.class, () -> new Sequence().search(null));
        assertThrows(NullPointerException.class, () -> new Parallel().search(null));
        assertThrows(NullPointerException.class, () -> new Thread(2).search(null));
    }

    @Test
    public void test2() {
        int size = 1000000;
        int[] array = new int[size];
        Arrays.fill(array, 0, size, 9679);
        assertFalse(new Sequence().search(array));
    }

    @Test
    public void test3() {
        int size = 1000000;
        int[] array = new int[size];
        Arrays.fill(array, 0, size - 1, 9679);
        array[size - 1] = 4;
        assertTrue(new Sequence().search(array));
    }

    @Test
    public void test4() {
        int size = 1000000;
        int[] array = new int[size];
        Arrays.fill(array, 0, size, 9679);
        assertFalse(new Parallel().search(array));
    }

    @Test
    public void test5() {
        int size = 1000000;
        int[] array = new int[size];
        Arrays.fill(array, 0, size - 1, 9679);
        array[size - 1] = 4;
        assertTrue(new Parallel().search(array));
    }

    @Test
    public void test6() {
        int size = 1000000;
        int[] array = new int[size];
        Arrays.fill(array, 0, size, 9679);
        assertFalse(new Thread(2).search(array));
    }

    private int[] createArray() {
        int[] array = new int[1000000];
        Arrays.fill(array, 0, 1000000 - 1, 1073676287);
        array[1000000 - 1] = 187263196;
        return array;
    }

    int[] arr = createArray();

    @Test
    public void superTest1() {
        long f = System.currentTimeMillis();
        boolean res = new Sequence().search(arr);
        long s = System.currentTimeMillis();
        System.out.println("sequence: " + (s - f));
        assertTrue(res);
    }

    @Test
    public void superTest2() {
        for (int i = 2; i < 6; i++) {
            long f = System.currentTimeMillis();
            boolean res = new Thread(i).search(arr);
            long s = System.currentTimeMillis();
            System.out.println("thread " + i + ": " + (s - f));
            assertTrue(res);
        }
    }

    @Test
    public void superTest3() {
        long f = System.currentTimeMillis();
        boolean res = new Parallel().search(arr);
        long s = System.currentTimeMillis();
        System.out.println("parallel: " + (s - f));
        assertTrue(res);
    }
}
