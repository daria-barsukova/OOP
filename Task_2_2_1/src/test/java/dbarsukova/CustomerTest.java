package dbarsukova;

import dbarsukova.baker.Bakers;
import dbarsukova.order.Order;
import dbarsukova.queue.MyQueue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * task-2-2-1 test.
 */

class CustomerTest {
    @BeforeAll
    static void setUp() {
    orderQueue = new MyQueue<>(0);
    bakers = new Bakers(orderQueue);
    }

    @Test
    @DisplayName("Customers")
    void customers_test() {
    Assertions.assertTrue(orderQueue.isEmpty());
    Thread customersThread = new Thread(bakers);
    customersThread.start();
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException ignored) {}
        bakers.stop();
    Assertions.assertFalse(orderQueue.isEmpty());
    }

    private static Bakers bakers;  // for generating orders
    private static MyQueue<Order> orderQueue;  // list of orders
    private final static int SLEEP_TIME = 1000;  // time for testing
}
