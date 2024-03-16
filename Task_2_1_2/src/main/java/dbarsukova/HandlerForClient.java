package dbarsukova;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.IllegalBlockingModeException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * class represents handler for individual client connections in server.
 * it processes tasks received from client and updates the server's task completion status.
 */

public class HandlerForClient implements Runnable {
    private final Socket clientSocket;
    private final AtomicInteger clientId;
    private final List<Integer> tasks;
    private final AtomicInteger completedTasks;

    /**
     * constructs new HandlerForClient instance with specified parameters.
     *
     * @param clientSocket   socket representing client connection.
     * @param clientId       atomic integer representing client's unique ID.
     * @param completedTasks atomic integer tracking number of completed tasks.
     * @param tasks          list of tasks to be processed by client.
     */
    public HandlerForClient(Socket clientSocket,
                            AtomicInteger clientId,
                            AtomicInteger completedTasks,
                            List<Integer> tasks) {
        this.clientSocket = clientSocket;
        this.clientId = clientId;
        this.completedTasks = completedTasks;
        this.tasks = tasks;
    }

    @Override
    public void run() {
        int currentClientId = clientId.get();
        try {
            Scanner in = new Scanner(clientSocket.getInputStream());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            boolean running = true;
            out.println("Connection has been successfully established");
            out.flush();
            while (running) {
                try {
                    currentClientId = clientId.incrementAndGet();
                    int task = tasks.get(currentClientId);
                    out.println(task);
                    out.flush();
                } catch (IndexOutOfBoundsException | NullPointerException e) {
                    break;
                }
                if (in.hasNextInt() && in.nextInt() == 1) {
                    completedTasks.incrementAndGet();
                    running = false;
                }
            }
        } catch (IOException | IllegalBlockingModeException e) {
            handleException(currentClientId);
        }
    }

    /**
     * handles exceptions that occur during task processing by resetting current client ID.
     *
     * @param currentClientId ID of client being processed when exception occurred.
     */
    private void handleException(int currentClientId) {
        clientId.set(currentClientId);
    }
}
