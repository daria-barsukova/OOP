package dbarsukova;

import java.io.IOException;


/**
 * class represents thread that simulates client making request to server.
 */

public class ClientThread implements Runnable {

    @Override
    public void run() {
        try {
            Client.client(new String[]{});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
