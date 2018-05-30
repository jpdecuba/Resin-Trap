package Server.LogServer;

import Shared.Model.User;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackEndServerLogs {


    private ServerSocket serverSocket;
    private Socket socket = null;
    private ExecutorService executor = Executors.newCachedThreadPool();
    public static ArrayList<User> sessions = new ArrayList<>();

    public BackEndServerLogs(int port) throws Exception {
        ServerSocketFactory socketFactory = (ServerSocketFactory) ServerSocketFactory.getDefault();
        serverSocket = (ServerSocket) socketFactory.createServerSocket(port);
    }

    public void runServer() {
        System.err.println("Waiting for connections...");

        while (true) {
            try {
                System.out.println("waiting for client...");
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            executor.submit(new BackEndThreadLogs(socket));
        }
    }

    public static void main(String args[]) throws Exception {
        BackEndServerLogs server2 = new BackEndServerLogs(7677);
        server2.runServer();

    }
}
