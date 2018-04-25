package Server;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackEndServer {


    private ServerSocket serverSocket;
    private Socket socket = null;

    private ExecutorService executor = Executors.newCachedThreadPool();

    public BackEndServer() throws Exception {
        ServerSocketFactory socketFactory = (ServerSocketFactory) ServerSocketFactory.getDefault();
        serverSocket = (ServerSocket) socketFactory.createServerSocket(7676);
    }

    private void runServer() {
        System.err.println("Waiting for connections...");

        while (true) {
            try {
                System.out.println("waiting for client...");
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            executor.submit(new BackEndThread(socket));
        }
    }

    public static void main(String args[]) throws Exception {
        BackEndServer server = new BackEndServer();
        server.runServer();
    }
}
