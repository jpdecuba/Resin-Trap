package Client.Sockets;

import Shared.Logging.LogConnection;
import Shared.Request.Response;
import javafx.beans.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Observer;
import java.util.Set;

public class ResponseThread extends Thread  {

    protected ObjectInputStream input;
    private SocketObserver observer;

    public ResponseThread(ObjectInputStream input, SocketObserver observer) {
        this.input = input;
        this.observer = observer;
    }

    public boolean run = true;

    @Override
    public void run() {

        try {
            System.err.println("Connecting started");

            while (run) {

                System.out.println("in loop");
                Object obj = input.readObject();
                Set<LogConnection> response = (Set<LogConnection>)obj;

                observer.object(response);

            }
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
