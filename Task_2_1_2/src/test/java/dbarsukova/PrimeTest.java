package dbarsukova;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;


/**
 * task-2-1-2 test.
 */

public class PrimeTest {
    static boolean result;

    @Test
    public void test1() {
        for (var a : Arrays.asList(7, 5, 7, 13, 5, 23, 2)) {
            assertTrue(IsPrime.isPrime(a));
        }
        for (var a : Arrays.asList(66, 88, 33, 4, 10)) {
            assertFalse(IsPrime.isPrime(a));
        }
    }

    @Test
    public void test2() throws InterruptedException {
        result = false;
        Thread serverThread = new Thread(new ServerThread(Arrays.asList(6, 6, 6, 6, 6, 3)));
        serverThread.start();
        new Thread(new ClientThread()).start();
        new Thread(new ClientThread()).start();
        serverThread.join();
        assertTrue(result);
    }
}
