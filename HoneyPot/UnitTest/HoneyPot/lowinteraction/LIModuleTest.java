package HoneyPot.lowinteraction;

import Client.HoneyPot.honeyrj.HoneyRJ;
import Client.HoneyPot.honeyrj.HoneyRJException;
import Client.HoneyPot.lowinteraction.LIModule;
import Client.HoneyPot.protocol.FtpProtocol;
import Shared.Logging.LogConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class LIModuleTest {
    LIModule mod;
    HoneyRJ parent;
    FtpProtocol prot;

    @Before
    public void setUp(){
        try {
            prot = new FtpProtocol();
            parent = new HoneyRJ();
            mod = new LIModule(prot, parent);
        } catch (HoneyRJException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addLog() {
        try {
            int before = mod.getParent().getLogs().size();
            LogConnection logConnection = new LogConnection(InetAddress.getByName("127.0.0.1"), InetAddress.getByName("127.0.0.2"), 50, 60, "FTP");
            mod.addLog(logConnection);
            Assert.assertEquals(1, mod.getParent().getLogs().size() - before);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getPort() {
        assertEquals(21, mod.getPort());
    }

    @Test
    public void getNumberConnections() {
        Assert.assertEquals(0, mod.getNumberOfActiveConnections());
        mod.setNumberConnectionsPlus();
        Assert.assertEquals(1, mod.getNumberOfActiveConnections());
        mod.setNumberConnectionsmin();
        Assert.assertEquals(0, mod.getNumberOfActiveConnections());
    }

    @Test
    public void setNumberConnectionsmin() {
        Assert.assertEquals(0, mod.getNumberOfActiveConnections());
        mod.setNumberConnectionsPlus();
        Assert.assertEquals(1, mod.getNumberOfActiveConnections());
        mod.setNumberConnectionsmin();
        Assert.assertEquals(0, mod.getNumberOfActiveConnections());
    }

    @Test
    public void setNumberConnectionsPlus() {
        Assert.assertEquals(0, mod.getNumberOfActiveConnections());
        mod.setNumberConnectionsPlus();
        Assert.assertEquals(1, mod.getNumberOfActiveConnections());
        mod.setNumberConnectionsmin();
        Assert.assertEquals(0, mod.getNumberOfActiveConnections());
    }

    @Test
    public void getParent() {
        assertEquals(parent, mod.getParent());
    }

    @Test
    public void stopInteractionModule() {
        try {
            mod.startInteractionModule();
            Assert.assertTrue(mod.isStarted());
            mod.stopInteractionModule();
            Assert.assertFalse(mod.isStarted());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shutdownInteractionModule() {
        try {
            mod.startInteractionModule();
            Assert.assertTrue(mod.isStarted());
            mod.shutdownInteractionModule();
            Assert.assertFalse(mod.isStarted());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ToString() {
        Assert.assertEquals("FTP", mod.toString());
    }

    @Test
    public void isStarted() {
        try {
            Assert.assertFalse(mod.isStarted());
            mod.startInteractionModule();
            Assert.assertTrue(mod.isStarted());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getLoggingDirectory() {
        Assert.assertNull(mod.getLoggingDirectory());
    }

    @Test
    public void getNumberOfActiveConnections() {
        Assert.assertEquals(0, mod.getNumberOfActiveConnections());
        mod.setNumberConnectionsPlus();
        Assert.assertEquals(1, mod.getNumberOfActiveConnections());
        mod.setNumberConnectionsmin();
        Assert.assertEquals(0, mod.getNumberOfActiveConnections());
    }
}