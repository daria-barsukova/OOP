package dbarsukova;

import dbarsukova.baker.Bakers;
import dbarsukova.order.Order;
import dbarsukova.queue.MyQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


/**
 * task-2-2-1 test.
 */

class CustomerTest {
    private static Bakers bakers;
    private static MyQueue<Order> orderQueue;

    @BeforeAll
    static void setUp() {
        orderQueue = new MyQueue<>(0);
        bakers = new Bakers(orderQueue);
    }

    @Test
    void test1() {
        Assertions.assertTrue(orderQueue.isEmpty());
        Thread customersThread = new Thread(bakers);
        customersThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bakers.stop();
        Assertions.assertFalse(orderQueue.isEmpty());
    }
}
