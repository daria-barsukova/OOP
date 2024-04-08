package dbarsukova;

import dbarsukova.baker.Restaurant;
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
 * task-2-2-1 restaurant test.
 */

class RestaurantTest {
    private static Random rand;
    private static List<Restaurant> restaurants;
    private static MyQueue<Order> orderQueue;
    private static MyQueue<Order> deliveryQueue;

    @BeforeAll
    static void setUp() {
        rand = new Random();
        orderQueue = new MyQueue<>(0);
        deliveryQueue = new MyQueue<>(10);
        IntStream.range(0, 10).forEach(i -> orderQueue.add(new Order(i, rand.nextInt(1000))));
        int[] time = new int[5];
        Arrays.setAll(time, i -> rand.nextInt(1000));
        restaurants = Arrays.stream(time)
                .mapToObj(bakingTime -> new Restaurant(orderQueue, deliveryQueue, bakingTime))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Test
    void test1() {
        Assertions.assertFalse(orderQueue.isEmpty());
        Assertions.assertTrue(deliveryQueue.isEmpty());
        restaurants.stream().map(Thread::new).forEach(Thread::start);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        restaurants.forEach(Restaurant::stop);
        Assertions.assertTrue(orderQueue.isEmpty());
        Assertions.assertFalse(deliveryQueue.isEmpty());
    }
}
