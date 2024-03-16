package dbarsukova;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * task-2-1-2 test.
 */

public class PrimeTest {
    static boolean result;

    @Test
    public void test1() {
        for (var a : Arrays.asList(7, 5, 7, 13, 5, 23, 2)) {
            Assertions.assertTrue(IsPrime.isPrime(a));
        }
        for (var a : Arrays.asList(66, 88, 33, 4, 10)) {
            Assertions.assertFalse(IsPrime.isPrime(a));
        }
    }
}
