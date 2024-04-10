package dbarsukova.baker;


/**
 * Baker class is abstract class that implements Runnable interface.
 * it defines basic structure for baker.
 */

public abstract class Baker implements Runnable {
    protected abstract void cooking();

    protected abstract void stop();

    @Override
    public void run() {
        cooking();
    }
}
