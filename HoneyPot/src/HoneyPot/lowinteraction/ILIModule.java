package HoneyPot.lowinteraction;

import HoneyPot.honeyrj.HoneyRJ;
import HoneyPot.logging.LogConnection;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface ILIModule  extends Serializable{



    public void addLog(LogConnection logConnection);
    public int getNumberConnections() ;

    public void setNumberConnections(int numberConnections) ;

    public int getPort();

    public void registerParent(HoneyRJ honey);

    public void run() ;

    public boolean resumeListeningForConnections();

    public HoneyRJ getParent();

    public void pauseListeningForConnections() ;

    public void startInteractionModule() throws IOException ;


    public void stopInteractionModule() ;

    public LIProtocol getProtocol();

    @Override
    public String toString() ;
    
     boolean isStarted() ;

    public File getLoggingDirectory();


    public int getNumberOfActiveConnections() ;


}
