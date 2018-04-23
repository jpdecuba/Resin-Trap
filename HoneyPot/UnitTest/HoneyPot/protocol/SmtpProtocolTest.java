package HoneyPot.protocol;

import Client.HoneyPot.lowinteraction.LIHelper;
import Client.HoneyPot.lowinteraction.TALK_FIRST;
import Client.HoneyPot.protocol.SmtpProtocol;

import static org.junit.Assert.*;

public class SmtpProtocolTest {

    private SmtpProtocol proto;

    @org.junit.Before
    public void setUp() throws Exception {

        proto = new SmtpProtocol();
    }

    @org.junit.Test
    public void whoTalksFirst() {
        assertEquals(TALK_FIRST.SVR_FIRST,proto.whoTalksFirst());
    }

    @org.junit.Test
    public void processInput() {

        assertEquals(LIHelper.vectorFromString("220 <domain> Service ready"),proto.processInput("connect"));
        assertEquals(LIHelper.vectorFromString("221 closing connection"),proto.processInput("quit"));

    }

    @org.junit.Test
    public void getPort() {

        assertEquals(25,proto.getPort());
    }



    @org.junit.Test
    public void Stringtest() {
        assertEquals("SMTP",proto.toString());
    }
}