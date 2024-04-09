package dbarsukova.baker;

import dbarsukova.order.Order;
import dbarsukova.queue.MyQueue;
import java.util.Random;


/**
 * Bakers class represents baker who generates orders and adds them to shared order queue.
 * it extends Baker class and overrides cooking method.
 */

public class Bakers extends Baker {
    private final MyQueue<Order> orderQueue;
    private volatile boolean isRunning;
    private final Random rand;
    private final Object lock = new Object();

    /**
     * constructs new Bakers object with specified order queue.
     *
     * @param orderQueue shared order queue where orders are added.
     */
    public Bakers(MyQueue<Order> orderQueue) {
        this.orderQueue = orderQueue;
        this.rand = new Random();
        this.isRunning = true;
    }

    /**
     * overrides cooking method from Baker class to generate orders
     * and add them to shared order queue.
     */
    @Override
    public void cooking() {
        for (int i = 0; ; i++) {
            if (!this.isRunning) {
                break;
            }
            Order order = new Order(i, rand.nextInt(1000));
            synchronized (lock) {
                this.orderQueue.add(order);
                order.printState();
                this.orderQueue.notifyForEmpty();
            }
            try {
                Thread.sleep(this.rand.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * stops generation of orders by setting isRunning flag to false.
     */
    @Override
    public void stop() {
        this.isRunning = false;
    }
}
