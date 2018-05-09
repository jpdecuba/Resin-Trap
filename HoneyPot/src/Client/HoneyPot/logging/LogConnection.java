package Client.HoneyPot.logging;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;

public class LogConnection implements Serializable, Comparable<LogConnection> {


    private InetAddress srcIP;
    private InetAddress dstIP;
    private int srcPort;
    private int dstPort;

    private Date date;

    private String Protocol;

    private int UserId;

    private String Username;

    private ArrayList<LogRecord> LogRecords = new ArrayList();

	public InetAddress getDstIP() {
		return dstIP;
	}

	public int getDstPort() {
		return dstPort;
	}

	public String getProtocol() {
		return Protocol;
	}

	public int getUserId() {return UserId;}

	public String getUsername() {return Username; }

	public void SetUsername(String username) {
	    this.Username = username;
    }

    /**
     * LogConnection Constructor
     * @param srcIP ip of the src
     * @param dstIP ip of the destination
     * @param srcPort source port
     * @param dstPort destination port
     * @param protocol protocol we used
     */
	public LogConnection(InetAddress srcIP, InetAddress dstIP, int srcPort, int dstPort, String protocol) {
        this.srcIP = srcIP;
        this.dstIP = dstIP;
        this.srcPort = srcPort;
        this.dstPort = dstPort;
        Protocol = protocol;
        date = new Date();
    }

    public LogConnection(InetAddress srcIP, InetAddress dstIP, int srcPort, int dstPort, String protocol, int userId, Date date) {
	    this(srcIP,dstIP,srcPort,dstPort,protocol);
        this.UserId = userId;
        this.date = date;
    }


    /**
     * AddLogRecord to Recordslist
     * @param Record Record to add
     */
    public void AddLogRecord(LogRecord Record) {
        LogRecords.add(Record);
    }

    public void AddLogRecord(ArrayList<LogRecord> Records) {
        LogRecords.addAll(Records);
    }


    public String message(){
        StringBuilder sb = new StringBuilder();
        sb.append("Service: "+ Protocol + " Remote ip:" + dstIP + ":" + dstPort + "\n");
        for(LogRecord item : LogRecords){
            sb.append("\t");
            sb.append(item.toString());
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return String.valueOf(getLogRecords().size());
    }

    /**
     * GetDate
     * @return Date
     */
    public Date getDate() {
        if(date == null){
            return new Date(0);
        }
        return date;
    }


    /**
     * Compareto on date
     * @param o Logconnection to match
     * @return int
     */
    @Override
    public int compareTo(LogConnection o) {

        return getDate().compareTo(o.getDate());
    }

    public ArrayList<LogRecord> getLogRecords() {
        return LogRecords;
    }
}
