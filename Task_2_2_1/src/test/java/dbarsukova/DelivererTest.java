package dbarsukova;

import dbarsukova.customer.Deliverer;
import dbarsukova.order.Order;
import dbarsukova.queue.MyQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


/**
 * task-2-2-1 deliverer test.
 */

class DelivererTest {
    private static Random rand;
    private static List<Deliverer> deliverers;
    private static MyQueue<Order> queue;

    @BeforeAll
    static void setUp() {
        rand = new Random();
        queue = new MyQueue<>(20);
        int[] capacity = new int[10];
        IntStream.range(0, 20).forEach(i -> queue.add(new Order(i, rand.nextInt(500))));
        Arrays.setAll(capacity, i -> rand.nextInt(5));
        deliverers = Arrays.stream(capacity)
                .mapToObj(delivererCapacity -> new Deliverer(queue, delivererCapacity))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Test
    void test1() {
        Assertions.assertFalse(queue.isEmpty());
        deliverers.stream().map(Thread::new).forEach(Thread::start);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        deliverers.forEach(Deliverer::stop);
        Assertions.assertTrue(queue.isEmpty());
    }
}
