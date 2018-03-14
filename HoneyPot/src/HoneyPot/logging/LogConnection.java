package HoneyPot.logging;

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


    /**
     * AddLogRecord to Recordslist
     * @param Record Record to add
     */
    public void AddLogRecord(LogRecord Record) {
        LogRecords.add(Record);
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
