package HoneyPot.logging;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LogTest {
    LogConnection logConnection;
    LogRecord rec;
    @Before
    public void Setup(){
        try {
            logConnection = new LogConnection(InetAddress.getByName("127.0.0.1"), InetAddress.getByName("127.0.0.2"), 50, 60, "FTP");
            Calendar today = new GregorianCalendar(2013,1,28,13,24,56);
            rec = new LogRecord(today.getTime(), "teststring", true);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void AddLogRecord(){
        logConnection.AddLogRecord(rec);
        Assert.assertEquals(logConnection.getLogRecords().get(0), rec);
    }

    @Test
    public void Message(){
        logConnection.AddLogRecord(rec);
        Assert.assertEquals("Service: FTP Remote ip:/127.0.0.2:60\n\t" +
        "Time: Thu Feb 28 13:24:56 CET 2013	Packet message:teststring  ,Received:true\n", logConnection.message());
    }

    @Test
    public void ToString(){
        logConnection.AddLogRecord(rec);
        Assert.assertEquals("1", logConnection.toString());
        Assert.assertEquals("Time: Thu Feb 28 13:24:56 CET 2013	Packet message:teststring  ,Received:true", rec.toString());
    }

    @Test
    public void GetDate(){
        logConnection.AddLogRecord(rec);
        Assert.assertEquals(new Date().getTime(), logConnection.getDate().getTime());
    }

    @Test
    public void CompareTo(){
        try {
            logConnection.AddLogRecord(rec);
            Assert.assertEquals(0, logConnection.compareTo(new LogConnection(InetAddress.getByName("127.0.0.1"), InetAddress.getByName("127.0.0.2"), 50, 60, "FTP")));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void GetLogRecords(){
        logConnection.AddLogRecord(rec);
        Assert.assertEquals(1, logConnection.getLogRecords().size());
    }

    @Test
    public void GetTimestamp(){
        logConnection.AddLogRecord(rec);
        Calendar today = new GregorianCalendar(2013,1,28,13,24,56);
        Assert.assertEquals(today.getTime(), rec.getTimestamp());
    }

    @Test
    public void GetPacket(){
        logConnection.AddLogRecord(rec);
        Assert.assertEquals("teststring", rec.getPacket());
    }

    @Test
    public void IsRcvd(){
        logConnection.AddLogRecord(rec);
        Assert.assertEquals(true, rec.isRcvd());
    }
}