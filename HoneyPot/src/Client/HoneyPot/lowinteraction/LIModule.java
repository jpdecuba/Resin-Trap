package Client.HoneyPot.lowinteraction;


import Client.HoneyPot.honeyrj.HoneyRJ;
import Shared.Logging.LogConnection;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An LIModule listens on a port specified by a LIProtocol and launches threads to handle client connections.
 * LIModules are part of a HoneyRJ
 *
 * @author Eric Peter
 * @see HoneyRJ
 * @see LIModuleThread
 */
public class LIModule implements ILIModule,Runnable,Serializable {

    /**
     * a reference to the protocol that this Module should run
     */
    LIProtocol _protocol;
    /**
     * the socket that listens for connections
     */
    private transient ServerSocket _server;

    private Set<LogConnection> logsFile;

    public void addLog(LogConnection logConnection) {
        _parent.addLog(logConnection);
    }

    private transient ExecutorService cachedPool = Executors.newCachedThreadPool();

    /**
     * return the port this Module listens on (as defined by the protocol)
     *
     * @return the port this Module listens on (as defined by the protocol)
     * @see LIProtocol#getPort()
     */
    public int getPort() {
        return _protocol.getPort();
    }

    /**
     * stores all the logRecords generated, sorted by timestamp
     * @see LogFile
     */
    //private TreeMap<Date,LogFile> _logFiles;

    /**
     * a reference to the containing HoneyRJ
     *
     * @see HoneyRJ
     */
    private HoneyRJ _parent;

    protected int numberConnections; //might be used later to limit # of connections at once.

    public int getNumberConnections() {
        return numberConnections;
    }

    public void setNumberConnectionsmin() {
        this.numberConnections--;
    }
    public void setNumberConnectionsPlus() {
        this.numberConnections++;
    }

    private boolean OnorOff = false;


    public boolean OnorOff(){
        return OnorOff;
    }
    private void SetOff(){
        OnorOff = false;

    }
    private void SetOn(){
        OnorOff = true;

    }

    /**

     * a internal reference to the thread this module is running in
     *
     * @see Thread
     */
    private transient volatile Thread _thread; //we start ourselves in a thread object so we can "safely" stop later (Java claims this is the right way)
    /**
     * true if the module is currently listening for client connections
     * NOTE: this is limited in the fact that if accept has already been called, that socket will still connect but another socket will not be created until listening = true.
     */
    private volatile boolean listening;

    /**
     * referr
     */


    /**
     * Create the module from the given protocol
     *
     * @param protocol LIProtcol object to implement here
     * @see LIProtocol
     */
	/*public LIModule(LIProtocol protocol) {
		_protocol = protocol;
		//_logFiles = new TreeMap<Date, LogFile>();
		logsFile = new HashSet<>();
		_thread = null;
        numberConnections = 0; //might be used later.
	}*/
    public LIModule(LIProtocol protocol, HoneyRJ honeyRJ) {
        _protocol = protocol;
        //_logFiles = new TreeMap<Date, LogFile>();
        logsFile = new HashSet<>();
        _thread = null;
        numberConnections = 0; //might be used later.

        _parent = honeyRJ;
    }

    /**
     * Called once a HoneyRJ adds this module to itself.
     * Allows this module to be created in memory but not yet registered to a HoneyRJ
     *
     * @param honey parent HoneyRJ
     * @see HoneyRJ#(LIModule)
     */
    public void registerParent(HoneyRJ honey) {
        _parent = honey;
    }

    /**
     * thread run method
     * start listening on the socket and launch sub-threads to handle connections when clients connect
     */
    public void run() {
        Thread thisThread = Thread.currentThread(); //by storing this, we can safely stop ourselves later
        while (_thread == thisThread) { //while running (so we can stop later)
            while (listening) { //allow us to temporary stop listening subject to limitation mentioned above
                try {
					Socket soc = _server.accept();
					if(soc != null) {
                        //new LIModuleThread(_server.accept(), this);
						cachedPool.submit(new LIModuleThread(soc, this));
					}

                    synchronized (this) {
                        numberConnections++;
                    }

                } catch (SocketException e) {
                    //let us die because someone stopped us
                    //e.printStackTrace();
                    synchronized (this) {
                        if (!(numberConnections <= 0)) {
                            numberConnections--;
                        }
                    }

                } catch (IOException e1) {
                    //let us die and retry loop
                    //e1.printStackTrace();
                    synchronized (this) {
                        if (!(numberConnections <= 0)) {
                            numberConnections--;
                        }
                    }

                } catch (LIModuleException e) {
                    // try again
                    //e.printStackTrace();

                }
                catch (NullPointerException ne)
				{
					//
				}
                try {

                    Thread.sleep(_parent.TIME_WAIT_CONNECTION);
                } catch (InterruptedException ignored) {
                    //prevent a DOS attack and high cpu during pause state
                }
            }

            try {
                Thread.sleep(_parent.TIME_WAIT_CONNECTION);
            } catch (InterruptedException ignored) {
                //prevent a DOS attack and high cpu during pause state
            }
        }

        //if we got here, the thread is dead, let us die

    }

    /**
     * instruct the module to begin listening
     * does nothing if module is already started
     */
    public boolean resumeListeningForConnections() {

        if (_server == null)
            try {
                _server = new ServerSocket(_protocol.getPort());
            } catch (IOException e) {
                return false;
            } //create socket, let our caller deal with an exception
        listening = true;
        return true;
    }

    /**
     * @return HoneyRJ that is our parent
     */
    public HoneyRJ getParent() {
        return _parent;
    }

    /**
     * Stop listening for client connections temporarily
     */
    public void pauseListeningForConnections() {
        listening = false;
        try {
            if (_server != null) {
                _server.close();
            }
        } catch (IOException ignored) {

        }
        _server = null;
    }

    /**
     * Launch into a thread and start listening for client connections
     *
     * @throws IOException if the socket can't be started
     */
    public void startInteractionModule() throws IOException {
        listening = true;
        SetOn();
        if (_server == null) //create the socket
            _server = new ServerSocket(_protocol.getPort()); //create socket, let our caller deal with an exception
//			_server.set

        if (_thread == null) { //create the thread
            _thread = new Thread(this);
            _thread.start();
        }
    }

    /**
     * Stops listening, stores the log into the HoneyRJ parent and stops the thread.
     */
    public void stopInteractionModule() {
        listening = false;
        numberConnections = 0;
        SetOff();


        try {
            if (_server != null) {
                _server.close();
            }
        } catch (IOException ignored) {

        }


        _thread = null;
        _server = null;
    }


    public void shutdownInteractionModule() {
        listening = false;
        numberConnections = 0;


        try {
            if (_server != null) {
                _server.close();
            }
        } catch (IOException ignored) {

        }


        _thread = null;
        _server = null;
    }


    /**
     * @return the protocol
     */
    public LIProtocol getProtocol() {
        return _protocol;
    }

    @Override
    public String toString() {
        return _protocol.toString();
//		return "Module for: " + _protocol.toString();
    }

    /**
     * Has our thread been created/socket started?
     *
     * @return True if yes.
     */
    public boolean isStarted() {
        return (_thread != null && _server != null);
    }

    /**
     * A child thread that is communicating with a client should call this method upon closing the connection to store the log
     */
//	protected void receiveLogFileFromThread(LogFile file) {
//		//file.LogToConsole(); //TODO debug method only
//		_logFiles.put(file.getStartedDate(),file);
//		_parent.storeLogFiles(this, file);
//		numberConnections--;
//		if(_gui != null)	_gui.setNumberConnections(numberConnections);
//	}
    public File getLoggingDirectory() {
        return _parent.getLoggingDirectory();
    }

    /**
     * Increment the number of active connections by 1
     */

    /**
     * return the number of active threads
     *
     * @return the number of active threads
     */
    public int getNumberOfActiveConnections() {
        return numberConnections;
    }


}
