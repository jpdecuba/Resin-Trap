package HoneyPot.logging;

import java.net.InetAddress;
import java.util.Date;

public class LogRecord {
	private Date Timestamp;
	private String Packet;
	private boolean Rcvd;


	public LogRecord(Date timestamp, String packet, boolean rcvd) {
		Timestamp = timestamp;
		Packet = packet;
		Rcvd = rcvd;
	}

	public String toString() {
		return "Time: " + Timestamp + "	Packet message:" + Packet + "  ,Received:" + Rcvd;
	}


	
	
	
	
}
