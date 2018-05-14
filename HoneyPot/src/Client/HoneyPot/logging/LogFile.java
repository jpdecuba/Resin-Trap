package Client.HoneyPot.logging;


import Client.HoneyPot.lowinteraction.LIModuleThread;
import Client.HoneyPot.honeyrj.HoneyRJ;
import Shared.Logging.LogRecord;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;


/**
 * a container for multiple log records
 * @author Eric Peter
 * @see LogRecord
 *
 */
public class LogFile {
	
	private PrintStream _logFilePrinter;
	private ArrayList<LogRecord> _logRecords;
	private String start;
	private String end;
	private Date started;
	private Date stopped;
	private LIModuleThread loggingForThread;

	public LogFile(LIModuleThread loggingForThread) throws Exception {
		this.loggingForThread = loggingForThread;
		String path = System.getenv("APPDATA") + "/Honeypot/";
		new File(path).mkdirs();
		_logRecords = new ArrayList<LogRecord>();

		String filename = path + loggingForThread.getProtocol().toString() + "_" +loggingForThread.getStartedTime().getTime() + ".log";
		try {
			_logFilePrinter = new PrintStream(new FileOutputStream(filename, false));
			setBeginningLogInfo();
		} catch (FileNotFoundException e) {
			throw new Exception("failed to create log file");
		}
	}
	
	/**
	 * call upon the beginning of a connection to put basic information into the start of the log record
	 * records a started logging time
	 */
	public void setBeginningLogInfo() {
		started = new Date();

		writeLogLine(loggingForThread.toString());
		writeLogLine("***********************************************");
		writeLogLine("*****Started at: " + started + "*******");
		writeLogLine("TIMESTAMP,SRC_IP:PRT,DST_IP:PRT,PACKET");
	}

	/**
	 * add a LogRecord as part of the log
	 * @param logToAdd LogRecord to add
	 */
	public void add(LogRecord logToAdd) {
		_logRecords.add(logToAdd);
		writeLogLine(logToAdd.toString());
	}

	/**
	 * call upon the end of a connection to put basic information into the end of the log record
	 * records a stopped logging time
	 */
	public void setEndingLogInfo() {
		this.stopped = new Date();
		writeLogLine("*****Stopped at: " + stopped + "*******");
		writeLogLine("***********************************************");
		closeLogFile();
	}

	/**
	 * return the Date when the log was started
	 * @return the Date when the log was started
	 */
	public Date getStartedDate() {
		return started;
	}

	/**
	 * print log record out to the console
	 */
	public void LogToConsole() {
		LogToStream(System.out);
	}

	/**
	 * print the LogRecord to the given PrintStream
	 * @param out  printstream
	 */
	public void LogToStream(PrintStream out) {
		if(started == null) { //sanity check if the thread crashed before logging started
			out.println("Connection failed before logging could start.");
			return;
		}
		
		out.println("***********************************************");
		out.println("*****Started at: " + started + "*******");
		//out.println(start);
		out.println("TIMESTAMP,                   SRC_IP:PRT,    DST_IP:PRT,    PACKET");
							
		
		for (LogRecord r : _logRecords) {
			out.println(r);
		}
		
		if(end != null)		out.println(end); //sanity check if connection failed before logging ended
		else				out.println("Connection terminated early due to failure");
		if(stopped != null)	out.println("*****Stopped at: " + stopped + "*******");
		out.println("***********************************************");
	}

	private void writeLogLine(String s) {
		_logFilePrinter.println(s);
		if(HoneyRJ.LOG_TO_CONSOLE) System.out.println(s);
	}

	private void closeLogFile() {
		_logFilePrinter.flush();
		_logFilePrinter.close();
	}
}
