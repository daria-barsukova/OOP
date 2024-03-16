package dbarsukova;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * class represents client that connects to server
 * and sends numbers to check if they are prime.
 */

public class Client {

    /**
     * main method of class.
     *
     * @throws IOException if I/O error occurs when creating socket or getting input/output streams.
     */
    public static void client() throws IOException {
        Socket socket = new Socket("localhost", 8000);
        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        while (in.hasNextLine()) {
            String input = in.nextLine();
            if (!input.isEmpty()) {
                System.out.println(input);
            }
            if (in.hasNextInt()) {
                int isPrime = IsPrime.isPrime(in.nextInt()) ? 1 : 0;
                out.println(isPrime);
            }
        }
        in.close();
        out.close();
        socket.close();
    }
}
