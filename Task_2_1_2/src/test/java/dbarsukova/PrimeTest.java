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

    @Test
    public void test2() throws InterruptedException {
        result = false;
        Thread serverThread = new Thread(new ServerThread(Arrays.asList(6, 6, 6, 6, 6, 3)));
        serverThread.start();
        new Thread(new ClientThread()).start();
        new Thread(new ClientThread()).start();
        serverThread.join();
        Assertions.assertTrue(result);
    }

    /**
     * class represents thread that simulates client making request to server.
     */

    public static class ClientThread implements Runnable {

        @Override
        public void run() {
            try {
                Client.client();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * class represents thread that simulates
     * server receiving data and processing it.
     */

    public static class ServerThread implements Runnable {
        private final List<Integer> data;

        /**
         * constructs new ServerThread with given data.
         *
         * @param data list of integers representing data to be processed by server.
         */
        public ServerThread(List<Integer> data) {
            this.data = data;
        }

        @Override
        public void run() {
            var server = new Server();
            try {
                result = server.startServer(data);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
