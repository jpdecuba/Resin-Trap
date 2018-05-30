package Client.Sockets;

import Client.Main.Main;
import Shared.Logging.LogConnection;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class LogManager implements Observer {

    private LogSocket socket;
    private ResponseThread responsethread;
    private SocketObserver observer;

    public LogManager() {
        this.observer = new SocketObserver();
        this.observer.addObserver(this);
        this.socket = new LogSocket(observer);
    }

    public LogSocket getSocket() {
        return socket;
    }

    public void setSocket(LogSocket socket) {
        this.socket = socket;
    }

    public ResponseThread getResponsethread() {
        return responsethread;
    }

    public void setResponsethread(ResponseThread responsethread) {
        this.responsethread = responsethread;
    }

    public SocketObserver getObserver() {
        return observer;
    }

    public void setObserver(SocketObserver observer) {
        this.observer = observer;
    }

    @Override
    public void update(Observable o, Object arg) {
        Main.SetDBLogs((Set<LogConnection>) arg);
    }
}
