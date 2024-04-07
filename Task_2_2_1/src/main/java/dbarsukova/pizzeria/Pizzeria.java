package dbarsukova.pizzeria;

import dbarsukova.baker.Bakers;
import dbarsukova.baker.Restaurant;
import dbarsukova.customer.Deliverer;
import dbarsukova.order.Order;
import dbarsukova.queue.MyQueue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Pizzeria class represents pizzeria with multiple restaurants,
 * bakers, and deliverers that process orders through delivery system.
 */

public class Pizzeria {
    private final MyQueue<Order> orderQueue;
    private final MyQueue<Order> deliveryQueue;
    private final Bakers bakers;
    private final List<Restaurant> restaurants;
    private final List<Deliverer> deliverers;

    /**
     * constructs new Pizzeria object using provided configuration.
     *
     * @param config configuration for pizzeria.
     */
    public Pizzeria(Configuration config) {
        this.orderQueue = new MyQueue<>(0);
        this.deliveryQueue = new MyQueue<>(config.getQueueCapacity());
        this.bakers = new Bakers(this.orderQueue);
        this.restaurants = Arrays.stream(config.getTime())
                .mapToObj(time -> new Restaurant(this.orderQueue, this.deliveryQueue, time))
                .collect(Collectors.toList());
        this.deliverers = Arrays.stream(config.getDelivererCapacity())
                .mapToObj(capacity -> new Deliverer(this.deliveryQueue, capacity))
                .collect(Collectors.toList());
    }

    /**
     * starts processing of orders by starting threads for bakers,
     * restaurants, and deliverers.
     */
    public void start() {
        new Thread(this.bakers).start();
        for (Restaurant rest : restaurants) {
            new Thread(rest).start();
        }
        for (Deliverer deliverer : deliverers) {
            new Thread(deliverer).start();
        }
    }

    /**
     * stops processing of orders and notifies all threads to stop.
     */
    public void stop() {
        this.bakers.stop();
        synchronized (orderQueue) {
            orderQueue.notifyAll();
        }
        for (Restaurant rest : restaurants) {
            rest.stop();
            synchronized (deliveryQueue) {
                deliveryQueue.notifyAll();
            }
        }
        for (Deliverer deliverer : deliverers) {
            deliverer.stop();
        }
    }
}
