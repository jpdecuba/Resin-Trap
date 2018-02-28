package HoneyPot.logging;

import java.net.InetAddress;
import java.util.ArrayList;

public class LogConnection {


    private InetAddress srcIP;
    private InetAddress dstIP;
    private int srcPort;
    private int dstPort;

    private String Protocol;

    private ArrayList<LogRecord> LogRecords = new ArrayList();

    public LogConnection(InetAddress srcIP, InetAddress dstIP, int srcPort, int dstPort, String protocol) {
        this.srcIP = srcIP;
        this.dstIP = dstIP;
        this.srcPort = srcPort;
        this.dstPort = dstPort;
        Protocol = protocol;
    }


    public void AddLogRecord(LogRecord Record) {
        LogRecords.add(Record);
    }


    public String message(){
        StringBuilder sb = new StringBuilder();
        sb.append("Service: "+ Protocol + " Remote ip:" + dstIP + ":" + dstPort);
        sb.append("\n");
        for(LogRecord item : LogRecords){
            sb.append("\t");
            sb.append(item.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
}
