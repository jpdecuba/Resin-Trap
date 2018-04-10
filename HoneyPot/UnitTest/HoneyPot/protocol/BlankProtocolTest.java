package HoneyPot.protocol;

import HoneyPot.lowinteraction.LIHelper;
import HoneyPot.lowinteraction.TALK_FIRST;
import org.junit.Before;

import static org.junit.Assert.*;

public class BlankProtocolTest {

    private BlankProtocol proto;

    @org.junit.Before
    public void setUp() throws Exception {
        proto = new BlankProtocol(5000);
    }

    @org.junit.Test
    public void whoTalksFirst() {
        assertEquals(TALK_FIRST.SVR_FIRST,proto.whoTalksFirst());
    }

    @org.junit.Test
    public void processInput() {
        assertEquals(LIHelper.vectorFromString("1.3.2"),proto.processInput(null));
    }

    @org.junit.Test
    public void getPort() {
        assertEquals(5000,proto.getPort());
    }



    @org.junit.Test
    public void Stringtest() {
        assertEquals("5000",proto.toString());
    }
}