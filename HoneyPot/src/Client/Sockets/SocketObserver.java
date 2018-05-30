package Client.Sockets;

import Shared.Logging.LogConnection;

import java.util.Observable;
import java.util.Set;

public class SocketObserver extends Observable{

    public void object(Object obj){
        setChanged();
        notifyObservers(obj);

    }

    public void Logs(Set<LogConnection> obj){
        setChanged();
        notifyObservers(obj);

    }
}
