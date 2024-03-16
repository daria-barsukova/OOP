package dbarsukova;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * class represents server that manages client connections and task completion status.
 */

public class Server {
    static final AtomicInteger clientId = new AtomicInteger();
    static List<Integer> tasks;
    static final AtomicInteger completedTasks = new AtomicInteger();

    /**
     * starts server with specified list of tasks.
     *
     * @param tasks list of tasks to be processed by clients.
     * @return true if at least one task was completed successfully, false otherwise.
     * @throws IOException          if I/O error occurs.
     * @throws InterruptedException if thread is interrupted while waiting for tasks to be completed.
     */
    public boolean startServer(List<Integer> tasks) throws IOException, InterruptedException {
        completedTasks.set(0);
        clientId.set(0);
        Server.tasks = tasks;
        Thread acceptThread = new Thread(new HandlerForConnections());
        acceptThread.start();
        waitForTasksToBeCompleted(acceptThread);
        return completedTasks.get() > 0;
    }

    /**
     * waits for all tasks to be completed before interrupting and joining the accept thread.
     *
     * @param acceptThread thread responsible for accepting client connections.
     * @throws InterruptedException if thread is interrupted while waiting.
     */
    private void waitForTasksToBeCompleted(Thread acceptThread) throws InterruptedException {
        while (true) {
            if (tasks.size() <= clientId.get() || completedTasks.get() != 0) {
                break;
            }
        }
        acceptThread.interrupt();
        acceptThread.join();
    }
}
