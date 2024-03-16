package dbarsukova;

import static dbarsukova.Server.clientId;
import static dbarsukova.Server.completedTasks;
import static dbarsukova.Server.tasks;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


/**
 * class represents handler for incoming client connections in server.
 * it listens for client connections on port 8000 and creates new thread to handle each client.
 */

public class HandlerForConnections implements Runnable {

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            List<Thread> clientThreads = new ArrayList<>();
            int connectedClients = 0;
            serverSocket.setSoTimeout(1000);
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    Thread clientThread = new Thread(new HandlerForClient(clientSocket,
                            clientId,
                            completedTasks,
                            tasks));
                    clientThread.start();
                    clientThreads.add(clientThread);
                    System.out.println("New client connected");
                    connectedClients++;
                } catch (SocketTimeoutException e) {
                    System.out.println("Socket accept timeout occurred");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            joinClientThreads(clientThreads, connectedClients);
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * joins threads of connected clients to wait for them to finish processing.
     *
     * @param clientThreads    list of threads representing connected clients.
     * @param connectedClients number of clients that have connected.
     */
    private void joinClientThreads(List<Thread> clientThreads, int connectedClients) {
        for (int i = 0; i < connectedClients; i++) {
            try {
                clientThreads.get(i).join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted while waiting for client thread to join");
            }
        }
    }
}
