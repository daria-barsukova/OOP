package dbarsukova;

import java.io.IOException;
import java.util.List;

import static dbarsukova.PrimeTest.result;


/**
 * class represents thread that simulates
 * server receiving data and processing it.
 */

public class ServerThread implements Runnable {
    private final List<Integer> data;

    /**
     * constructs new ServerThread with given data.
     *
     * @param data list of integers representing data to be processed by server.
     */
    public ServerThread(List<Integer> data) {
        this.data = data;
    }

    @Override
    public void run() {
        var server = new Server();
        try {
            result = server.startServer(data);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
