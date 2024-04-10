package dbarsukova.customer;


/**
 * Customer class is abstract class that implements Runnable interface.
 * It defines basic structure for customer in.
 */

public abstract class Customer implements Runnable {
    protected abstract void order();

    protected abstract void stop();

    @Override
    public void run() {
        order();
    }
}
