package Client.HoneyPot.lowinteraction;

import Client.HoneyPot.honeyrj.HoneyRJ;
import Client.HoneyPot.logging.LogConnection;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;


public interface ILIModule  extends Serializable{



    void addLog(LogConnection logConnection);
    int getNumberConnections() ;

    void setNumberConnectionsmin() ;
    void setNumberConnectionsPlus() ;
    int getPort();

    void registerParent(HoneyRJ honey);

    void run() ;

    boolean resumeListeningForConnections();

    HoneyRJ getParent();

    void pauseListeningForConnections() ;

     void startInteractionModule() throws IOException ;

     boolean OnorOff();



     void stopInteractionModule();
     void shutdownInteractionModule() ;

     LIProtocol getProtocol();

    @Override
     String toString() ;

     boolean isStarted() ;

     File getLoggingDirectory();


     int getNumberOfActiveConnections() ;


}
