package HoneyPot.protocol;

import HoneyPot.lowinteraction.LIHelper;
import HoneyPot.lowinteraction.LIProtocol;
import HoneyPot.lowinteraction.TALK_FIRST;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class BlankProtocol implements LIProtocol {
    private int port = 0;

    private int connectionState = 0;
    private static int KILLED = 666;

    public BlankProtocol(int port) {
        this.port = port;
    }

    @Override
    public TALK_FIRST whoTalksFirst() {
        return TALK_FIRST.SVR_FIRST;
    }

    @Override
    public Vector<String> processInput(String messageFromClient) {
        if(messageFromClient == null) {
            return LIHelper.vectorFromString("1.3.2");
        }
        return LIHelper.vectorFromString(messageFromClient);
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public boolean isConnectionOver() {
        return connectionState == KILLED;
    }


    @Override
    public String toString() {
        return String.valueOf(port);
    }
}
