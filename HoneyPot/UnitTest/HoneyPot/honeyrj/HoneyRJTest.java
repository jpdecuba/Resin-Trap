package HoneyPot.honeyrj;

import HoneyPot.lowinteraction.LIModule;
import HoneyPot.lowinteraction.LIProtocol;
import HoneyPot.protocol.BlankProtocol;
import HoneyPot.protocol.FtpProtocol;
import HoneyPot.protocol.IrcProtocol;
import HoneyPot.protocol.MySQLProtocol;
import org.junit.Test;

import static org.junit.Assert.*;

public class HoneyRJTest {
    HoneyRJ honeypot;
    LIModule ftpM;
    LIModule blankm;
    LIModule ircM;

    @org.junit.Before
    public void setUp() throws Exception {
        honeypot = new HoneyRJ();
        LIProtocol ftpP = new FtpProtocol();
        ftpM = new LIModule(ftpP,honeypot);
        LIProtocol ircP = new IrcProtocol();
        ircM = new LIModule(ircP,honeypot);

        BlankProtocol blank = new BlankProtocol(53);
        blankm = new LIModule(blank,honeypot);
        honeypot.RegisterService(ftpM);
        honeypot.RegisterService(blankm);
        //honeypot.startPort(21);
        honeypot.startPort(ircM.getPort());



    }

    @Test
    public void getLoggingDirectory() {

    }

    @Test
    public void startPort() {
        assertEquals(false,ftpM.isStarted());
        honeypot.startPort(ftpM.getPort());
        assertEquals(true,ftpM.isStarted());
    }

    @Test
    public void startModule() {
        assertEquals(false,blankm.isStarted());
        honeypot.startModule(blankm);
        assertEquals(true,blankm.isStarted());
    }



    @Test
    public void registerService() {
        assertEquals(false,honeypot._services.containsKey(6667));
        honeypot.RegisterService(ircM);
        assertEquals(true,honeypot._services.containsKey(6667));
    }

    @Test
    public void deRegisterService() {
        assertEquals(true,honeypot._services.containsKey(ftpM.getPort()));
        honeypot.DeRegisterService(ftpM);
        assertEquals(false,honeypot._services.containsKey(ftpM.getPort()));
    }



    @Test
    public void getNumberOfActiveConnections() {
        assertEquals(0,honeypot.getNumberOfActiveConnections(21));

    }

}