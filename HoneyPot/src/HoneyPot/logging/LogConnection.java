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

	public LogConnection(InetAddress srcIP, InetAddress dstIP, int srcPort, int dstPort, String protocol) {
        this.srcIP = srcIP;
        this.dstIP = dstIP;
        this.srcPort = srcPort;
        this.dstPort = dstPort;
        Protocol = protocol;

        date = new Date();
    }


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

    public Date getDate() {
        return date;
    }


    @Override
    public int compareTo(LogConnection o) {
        return getDate().compareTo(o.getDate());
    }
}
