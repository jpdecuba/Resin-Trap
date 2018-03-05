package HoneyPot.logging;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;

public class LogRecord implements Serializable {
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
