package Client.HoneyPot.honeyrj;



import Shared.Logging.LogConnection;
import Client.HoneyPot.lowinteraction.ILIModule;
import Client.HoneyPot.lowinteraction.LIDeserializeDBThread;
import Client.HoneyPot.lowinteraction.LIDeserializeThread;
import Client.HoneyPot.lowinteraction.LIModule;
import Client.Main.Main;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * HoneyRJ coordinates a number of LIModules, which provide faux services to hackers.
 * HoneyRJ is the final destination for all log files created by the LIModules.
 * @author Eric Peter
 * @see LIModule
 */
public class HoneyRJ  implements Serializable{
	private File _logging_dir;

	private Set<LogConnection> logs = new HashSet<>();
	
	public File getLoggingDirectory() {
		return _logging_dir;
	}

	/**
	 * HashMap of all service modules
	 */
	public HashMap<Integer, ILIModule> _services;
	/**
	 * all the logs we have collected
	 */
	//private HashMap<Integer, TreeMap<Date, LogFile>> _logs; //this storage sucks dick but will be improved
	/**
	 * The default time out for connections.  Any connection active after this period will be closed and a log entry will be created.
	 */
	public static final int DEFAULT_TIME_OUT_MS = 120000;
	/**
	 * Default logging directory
	 */
	public static final String DEFAULT_LOG_DIR="C:\\Users\\bartv\\Desktop\\HoneyRJ-master\\HoneyRJ-master";

	/**
	 * log file debugging
	 */
	public static final boolean LOG_TO_CONSOLE = false;
	/**
	 * Interval between connections for one protocol (in ms)
	 */
	public static final int TIME_WAIT_CONNECTION = 5000;

	/**
	 * constructor
	 * @throws HoneyRJException if the log directory can't be created
	 */
	public HoneyRJ() throws HoneyRJException {
		this(DEFAULT_LOG_DIR);
		//SetDBLog();

	}

	public void setDBLogs(Set<LogConnection> logs){
		if(logs != null) {
			this.logs = logs;
		}

	}


	public void SetDBLog() {
		if(Main.GetAccount().getName() != null && Main.SocketCheck()){
			try{
				Future<Set<LogConnection>> future = null;
				ExecutorService executorService = Executors.newSingleThreadExecutor();
				future = executorService.submit(new LIDeserializeDBThread());
				logs = future.get();
			} catch (InterruptedException | ExecutionException e){
				e.printStackTrace();
			}

		}

		if(logs == null){
			getLogsfromdir();
		}
	}

	/**
	 * 
	 * @param logDir logging directory to use -  C:\\tmp\\ is the default
	 * @throws HoneyRJException if the log directory can't be created
	 */
	public HoneyRJ(String logDir) throws HoneyRJException {
		_services = new HashMap<Integer, ILIModule>();
		//_logs = new HashMap<Integer, TreeMap<Date,LogFile>>();
		
		/*_logging_dir = new File(logDir + "rj_" + new Date().getTime() + "_log");
		//File loggingDir = 
		if(!_logging_dir.exists()) {
			if(!_logging_dir.mkdir())		throw new HoneyRJException("Failed to create log directory");
		}*/
	}
	
	/**
	 * attempt to start the module running on the given port
	 * @param portToStart int
	 * @return true on success, false if it fails to start or does not exist
	 */
	public boolean startPort(int portToStart) {
		if(!_services.containsKey(portToStart))	return false; //sanity
		try {
			_services.get(portToStart).startInteractionModule();
		} catch (IOException e) {
			return false; //FAILED TO START THREAD/server
		}
		return true;
	}

	
	/**
	 * grabs the port # from the module and starts the local copy
	 * @see HoneyRJ#startPort(int)
	 */
	public boolean startModule(ILIModule moduleToStart) {
		return startPort(moduleToStart.getProtocol().getPort());
	}
	
	/**
	 * register a given LIModule with this HoneyRJ
	 * @param moduleToBeConnected
	 * @return true on success, false if there already exists a Module on the port of moduleToBeConnected
	 * @see LIModule#registerParent(HoneyRJ)
	 */
	@SuppressWarnings("UnusedReturnValue")
	public boolean RegisterService(ILIModule moduleToBeConnected) {
		if(_services.containsKey(moduleToBeConnected.getPort())) { //already have a service registered on this port
			return false;
		} else {
			_services.put(moduleToBeConnected.getPort(), moduleToBeConnected); //add to list
			//_logs.put(moduleToBeConnected.getPort(), new TreeMap<Date, LogFile>());
			moduleToBeConnected.registerParent(this); //register with this HoneyRJ
			return true;
		}
	}
	
	/**
	 * disconnects the given module from the HoneyRJ
	 * the module will attempt to send its logs back to us
	 * @param moduleToBeDisconnected
	 * @return true on success, false if the module isn't in the HoneyRJ
	 */
	public boolean DeRegisterService(ILIModule moduleToBeDisconnected) {
		int port = moduleToBeDisconnected.getPort();
		if(_services.containsKey(port) && moduleToBeDisconnected == _services.get(port)) {
			moduleToBeDisconnected.stopInteractionModule();
			_services.remove(port);
			return true;
		} else {
			return false;
		}
		
	}


	public boolean ShutdownService(ILIModule moduleToBeDisconnected) {
		int port = moduleToBeDisconnected.getPort();
		if(_services.containsKey(port) && moduleToBeDisconnected == _services.get(port)) {
			moduleToBeDisconnected.shutdownInteractionModule();
			_services.remove(port);
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * Pause the given module from accepting connections
	 * @param moduleToPause
	 * @return true on success, false if the module isn't in the HoneyRJ
	 */
	public boolean PauseNewConnections(ILIModule moduleToPause) {
		if(_services.containsKey(moduleToPause.getPort()) && moduleToPause == _services.get(moduleToPause.getPort())) {
			moduleToPause.pauseListeningForConnections();
			return true;
		} else 	return false;
	}
	
	/**
	 * Resumes the given module to accepting connections
	 * @param moduleToResume
	 * @return true on success, false if the module isn't in the HoneyRJ
	 */
	public boolean ResumeNewConnections(ILIModule moduleToResume) {
		if(_services.containsKey(moduleToResume.getPort()) && moduleToResume == _services.get(moduleToResume.getPort())) {
			moduleToResume.resumeListeningForConnections();
			return true;
		} else	{
			return false;
		}
	}

	/**
	 * accept a collection of LogFiles from a module and store them
	 */
//	public void storeLogFiles(LIModule from, LogFile file) {
//		_logs.get(from.getPort()).put(file.getStartedDate(), file);
//	}
	
	public void DebugServices() {
		for(Integer i: _services.keySet()) {
			System.out.println("Port " + i + ": " + _services.get(i).toString());
		}
	}



	private void getLogsfromdir(){
		File file = new File(System.getenv("APPDATA") + "/Honeypot/AllLogs.txt");
		if(file.exists())
		{
			try {
				Future<Set<LogConnection>> future = null;
				ExecutorService executorService = Executors.newSingleThreadExecutor();
				future = executorService.submit(new LIDeserializeThread());
				logs = future.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

		}
	}

	public void addLog(LogConnection log){
		if(logs == null)
		{
			logs = new HashSet<>();
		}
		logs.add(log);
	}

	public LinkedList<LogConnection> getLogs() {
		if(logs == null)
		{
			return null;
		}
        LinkedList<LogConnection> list = new LinkedList<>(logs);


		if(list.size() <= 0)
		{
			return null;
		}

		Collections.sort(list);

		return list;
	}


	public int getNumberOfActiveConnections(int port ){

        return  _services.get(port).getNumberOfActiveConnections();


    }


    public boolean ServiceisStarted(int port ){

        return _services.get(port).isStarted();


    }






}
