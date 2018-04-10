package HoneyPot.protocol;

import HoneyPot.lowinteraction.LIHelper;
import HoneyPot.lowinteraction.TALK_FIRST;
import org.junit.Assert;

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