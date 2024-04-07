package dbarsukova.baker;

import dbarsukova.order.Order;
import dbarsukova.queue.MyQueue;


/**
 * Restaurant class extends Baker class and represents restaurant
 * processes orders through delivery system.
 */

public class Restaurant extends Baker {
    private final MyQueue<Order> orderQueue;
    private final MyQueue<Order> deliveryQueue;
    private final int time;
    private volatile boolean isRunning;

    /**
     * constructs new Restaurant object with specified order queue,
     * delivery queue, and preparation time.
     *
     * @param orderQueue    queue for incoming orders.
     * @param deliveryQueue queue for orders ready for delivery.
     * @param time          preparation time for each order.
     */
    public Restaurant(MyQueue<Order> orderQueue, MyQueue<Order> deliveryQueue, int time) {
        this.orderQueue = orderQueue;
        this.deliveryQueue = deliveryQueue;
        this.time = time;
        this.isRunning = true;
    }

    /**
     * implements cooking process where orders are processed and moved
     * from order queue to delivery queue.
     */
    @Override
    public void cooking() {
        while (this.isRunning) {
            while (this.orderQueue.isEmpty()) {
                try {
                    this.orderQueue.waitOnEmpty();
                } catch (InterruptedException e) {
                    if (!this.isRunning) {
                        break;
                    }
                }
            }
            if (!this.isRunning) {
                break;
            }
            Order order = this.orderQueue.remove();
            if (order == null) {
                continue;
            }
            order.updateState();
            order.printState();
            this.orderQueue.notifyForFull();
            try {
                Thread.sleep(this.time);
            } catch (InterruptedException ignored) {
            }
            while (this.deliveryQueue.isFull()) {
                try {
                    this.deliveryQueue.waitOnFull();
                } catch (InterruptedException e) {
                    if (!this.isRunning) {
                        break;
                    }
                }
            }
            if (!this.isRunning) {
                break;
            }
            this.deliveryQueue.add(order);
            order.updateState();
            order.printState();
            this.deliveryQueue.notifyForEmpty();
        }
    }

    /**
     * stops cooking process and notifies queues to resume or stop waiting.
     */
    @Override
    public void stop() {
        this.isRunning = false;
        this.orderQueue.notifyForEmpty();
        this.deliveryQueue.notifyForFull();
    }
}
