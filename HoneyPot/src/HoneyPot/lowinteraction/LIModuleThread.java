package HoneyPot.lowinteraction;



import HoneyPot.honeyrj.HoneyRJ;
import HoneyPot.logging.LogConnection;
import HoneyPot.logging.LogRecord;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

/**
 * Handles client connections that a LIModule receives
 * @author Eric Peter
 * @see LIModule
 *
 */
public class LIModuleThread implements Runnable {
	
	/**
	 * Time out in MSEC that is used
	 */
	
	//the important stuff
	/**
	 * socket with the client connection
	 */
	private Socket _socket;
	/**
	 * Protocol to communicate with
	 */
	private LIProtocol _protocol;
	/**
	 * Our parent thread/module
	 */
	private LIModule _parent;
	/**
	 * The logfile
	 */
	//private LogFile _logFile;
	//store some basic info later to make logging easier.
	/**
	 * Store the local IP address from the socket for reference
	 */
	private InetAddress localIP;
	/**
	 * Store the remote IP address from the socket for reference
	 */
	private InetAddress remoteIP;
	/**
	 * Store the local IP port from the socket for reference
	 */
	private int localPort;
	/**
	 * Store the remote IP port from the socket for reference
	 */
	private int remotePort;
	/**
	 * Used to determine when a connection times out
	 */
	private Date _startTime;

	private LogConnection logs;
	//private ArrayList<LogRecord> logs = new ArrayList<>();

	

	
	/**
	 * constructor: take a socket and the parent thread and make a local copy of the LIProtocol 
	 * @param accept Socket that the client is on
	 * @param parent LIModule that called us
	 * @throws LIModuleException  upon failure to initialize
	 */
	public LIModuleThread(Socket accept, LIModule parent) throws LIModuleException  {
		//initialize
		_socket = accept;
		
		//set the timeout value
		try {
			_socket.setSoTimeout(HoneyRJ.DEFAULT_TIME_OUT_MS);
		} catch (SocketException ignored) {

		}
		_parent = parent;
		try {
			_protocol = _parent.getProtocol().getClass().newInstance();
		} catch (Exception ignored) {

		}
		
		_startTime = new Date();
//		try {
//			_logFile = new LogFile(this);
//		} catch (LoggingException e) {
//			throw new LIModuleException("Failed to create log file");
//		}
		
		//store some basic information.
		localIP = _socket.getLocalAddress();
		remoteIP = _socket.getInetAddress();
		localPort = _socket.getLocalPort();
		remotePort = _socket.getPort();
		System.out.println("local:  " + localIP);
		System.out.println("remoteIP:  " + remoteIP);
		System.out.println("localPort:  " + localPort);
		System.out.println("remotePort:  " + remotePort);

		logs = new LogConnection(localIP, remoteIP,localPort,remotePort,_protocol.toString());

	}
	
	/**
	 * thread run method: starts the actual interaction with the client through Streams/Buffers
	 */
	public void run() {
		//_logFile.setBeginningLogInfo("*****Protocol " + _protocol + " is starting a connection to " + _socket.getInetAddress() + " using local port " + _socket.getLocalPort() + "****");
		
		PrintWriter out = null;
		BufferedReader in = null;
		

		try {
			
			//create the string reader/writers on the socket
		    out = new PrintWriter(_socket.getOutputStream(), true);
		    in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		    
		    String inputLine; //temporary strings
		    Vector<String> outputLines;
		    
		    //allow for varying protocols which have the server talk first or second
		    switch (_protocol.whoTalksFirst()) {
		    case SVR_FIRST:
		    	outputLines = _protocol.processInput(null); //get the first line from the server protocol
			    
		    	for(String outputLine : outputLines){
		    		out.println(outputLine); //send it
					AddSendLogRecord(outputLine);

		    	}
		    	break;
		    case CLIENT_FIRST:
			    inputLine = in.readLine(); //get the first line from the client
				AddRcvdLogRecord(inputLine);

		    	outputLines = _protocol.processInput(inputLine); //process it
		    	for(String outputLine : outputLines){
		    		out.println(outputLine); //and reply
					AddSendLogRecord(outputLine);

		    	}
		    	break;
		    }
		    
		    //now enter the main loop for the rest of the interaction (both cases just sent a message)
		    while ((new Date().getTime() - (_startTime.getTime()) <= HoneyRJ.DEFAULT_TIME_OUT_MS) && (inputLine = in.readLine()) != null) { //loop recieving messages
				AddRcvdLogRecord(inputLine);

		    	
		    	//parse and reply
		    	outputLines = _protocol.processInput(inputLine);
		    	for(String outputLine : outputLines){
		    		if (outputLine != null){
		    			out.println(outputLine);
						AddSendLogRecord(outputLine);
		    			//_logFile.add(createSentLogEntry(outputLine)); //log the sent message
		    		}
		    	}
				if (_protocol.isConnectionOver()) //if the protocol think the connection is over, stop listening to the client
				    break;
		    }
		    //clean up the connection
		    out.close();
		    in.close();


		} catch (SocketTimeoutException e ){
			if (out != null){
				out.close();
			}
			try {
				if (in != null) {
					in.close();
				}
				_socket.close();
			} catch (IOException ignored) {

			}
			//Last
		} catch (IOException e) {
			//e.printStackTrace();
		    //_logFile.setEndingLogInfo("*****Protocol " + _protocol + " FAILED talking to " + _socket.getInetAddress() + " using local port " + _socket.getLocalPort() +", connection closed.****");
		} finally {
			//report what is available of the log back to the parent
			LogRecord();
		}	
	}
	
	/**
	 * make a LogRecord object for a received packet
	 * @param packet String
	 * @return LogRecord with appropriate information
	 */
//	private LogRecord createRcvdLogEntry(String packet) {
//		LogRecord r = new LogRecord(new Date(),packet,true);
//		r.setSrcIP(remoteIP);
//		r.setDstIP(localIP);
//		r.setDstPort(remotePort);
//		r.setSrcPort(localPort);
//		r.setPacket(packet);
//		r.setTimestamp(new Date());
//		return r;
//	}


	private void AddRcvdLogRecord(String packet) {
		LogRecord r = new LogRecord(new Date(),packet,true);
		logs.AddLogRecord(r);

	}


	private void AddSendLogRecord(String packet) {
		LogRecord r = new LogRecord(new Date(),packet,false);

		logs.AddLogRecord(r);

	}


	private void LogRecord(){

		System.out.println(logs.message());


	}


	public Date getStartedTime() {
		return _startTime;
	}

	public LIProtocol getProtocol() {
		return _protocol;
	}




}
