package HoneyPot.protocol;

import HoneyPot.lowinteraction.LIHelper;
import HoneyPot.lowinteraction.LIProtocol;
import HoneyPot.lowinteraction.TALK_FIRST;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Vector;

public class DNSProtocol implements LIProtocol {
    private int connectionstate = 0;
    @Override
    public TALK_FIRST whoTalksFirst() {
        return TALK_FIRST.CLIENT_FIRST;
    }

    @Override
    public Vector<String> processInput(String messageFromClient) {

        return null;
    }


    public byte[] processInputbyte(byte[] messageFromClient) {
        if(connectionstate != 0){
            return messageFromClient;
        }
        connectionstate++;
        String hexString = "aaaa81800001000100000000076578616d706c6503636f6d0000010001c00c00010001000034b400045db8d822";
        byte[] bytes = DatatypeConverter.parseHexBinary(hexString);
        return bytes;
    }


    @Override
    public int getPort() {
        return 53;
    }

    @Override
    public boolean isConnectionOver() {
        return false;
    }
    @Override
    public String toString(){
        return "DNS";
    }
}
