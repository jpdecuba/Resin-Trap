package Shared.Logging;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;

public class LogRecord implements Serializable {
	private Date Timestamp;
	private String Packet;
	private boolean Rcvd;


	/**
	 * LogRecord Constructor
	 * @param timestamp time that the packet was send
	 * @param packet message send
	 * @param rcvd boolean if it send or received
	 */
	public LogRecord(Date timestamp, String packet, boolean rcvd) {
		Timestamp = timestamp;
		Packet = packet;
		Rcvd = rcvd;
	}
    @Override
	public String toString() {
		return "Time: " + Timestamp + "	Packet message:" + Packet + "  ,Received:" + Rcvd;
	}

    public Date getTimestamp() {
        return Timestamp;
    }

    public String getPacket() {
        return Packet;
    }

    public boolean isRcvd() {
        return Rcvd;
    }
}
