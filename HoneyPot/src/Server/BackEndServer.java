package Server;

import Server.LogServer.BackEndServerLogs;
import Shared.Model.User;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackEndServer {


    private ServerSocket serverSocket;
    private Socket socket = null;
    private ExecutorService executor = Executors.newCachedThreadPool();
    public static ArrayList<User> sessions = new ArrayList<>();

    public BackEndServer(int port) throws Exception {
        ServerSocketFactory socketFactory = (ServerSocketFactory) ServerSocketFactory.getDefault();
        serverSocket = (ServerSocket) socketFactory.createServerSocket(port);
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
        BackEndServer server = new BackEndServer(7676);
        server.runServer();
        BackEndServerLogs server2 = new BackEndServerLogs(7677);
        server2.runServer();
    }
}
