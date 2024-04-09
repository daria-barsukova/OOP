package dbarsukova.customer;

import dbarsukova.order.Order;
import dbarsukova.queue.MyQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Deliverer class represents deliverer who picks up orders from shared
 * order queue and delivers them to customers.
 * it extends Customer class and overrides order method to pick up orders
 * from queue and deliver them.
 */

public class Deliverer extends Customer {
    private final MyQueue<Order> queue;
    private final int capacity;
    private volatile boolean isRunning;
    private final Random rand;
    private final Object lock = new Object();

    /**
     * constructs new Deliverer object with specified order queue and capacity.
     *
     * @param queue    shared order queue where orders are picked up.
     * @param capacity maximum number of orders that can be picked up at once.
     */
    public Deliverer(MyQueue<Order> queue, int capacity) {
        this.queue = queue;
        this.capacity = capacity;
        this.isRunning = true;
        this.rand = new Random();
    }

    /**
     * overrides order method from Customer class to pick up
     * orders from queue and deliver them to customers.
     */
    @Override
    public void order() {
        while (this.isRunning && !Thread.interrupted()) {
            List<Order> orders = new ArrayList<>(this.capacity);
            synchronized (lock) {
                for (int i = 0; i < this.capacity; i++) {
                    while (this.queue.isEmpty() && this.isRunning) {
                        try {
                            this.queue.waitOnEmpty();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    if (!this.isRunning || Thread.interrupted()) {
                        break;
                    }
                    Order order = this.queue.remove();
                    if (order == null) {
                        i--;
                        continue;
                    }
                    orders.add(order);
                    this.queue.notifyForFull();
                }
            }
            for (Order order : orders) {
                order.updateState();
                order.printState();
                try {
                    Thread.sleep(order.getTime());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                order.updateState();
                order.printState();
            }
            try {
                Thread.sleep(this.rand.nextInt(500));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * stops delivery process by setting isRunning flag to false
     * and notifying queue for empty.
     */
    @Override
    public void stop() {
        this.isRunning = false;
        this.queue.notifyForEmpty();
    }
}
