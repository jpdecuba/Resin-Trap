package HoneyPot.protocol;

import HoneyPot.lowinteraction.LIHelper;
import HoneyPot.lowinteraction.LIProtocol;
import HoneyPot.lowinteraction.TALK_FIRST;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class SmtpProtocol implements LIProtocol {


    private String Service = "SMTP";


    private int state = 0;

    private final int login = 302;

    private final int auth = 300;

    private final int authdone = 301;

    private final int sendmail = 303;

    private final int killed = 666;

    private String emailfrom = "";
    private String emailsend = "";

    private String domain = "exampledomain.com";




    @Override
    public TALK_FIRST whoTalksFirst() {
        return TALK_FIRST.SVR_FIRST;
    }

    @Override
    public Vector<String> processInput(String messageFromClient) {
        List<String> msg = null;
        if(messageFromClient != null) {
            if (messageFromClient.length() != 0) {
                msg = Arrays.asList(messageFromClient.split(" "));
            }

            if(messageFromClient.toUpperCase().equals("QUIT"))
            {
                return LIHelper.vectorFromString("221 closing connection");
            }


            switch (state) {

                case 0:
                    if (messageFromClient.toLowerCase().contains("connect")) {
                        state = 2;
                        return LIHelper.vectorFromString("220 <domain> Service ready");
                    }
                case 2:
                    if (msg.get(0).toUpperCase().contains("HELO") || msg.get(0).toUpperCase().contains("EHLO")) {
                        if (msg.size() >= 1) {

                            return LIHelper.vectorFromString("250-" + msg.get(1) + " Hello  [1.1.1.2]\n" +
                                    "250-SIZE 52428800\n" +
                                    "250-PIPELINING\n" +
                                    "250-AUTH PLAIN LOGIN\n" +
                                    "250-STARTTLS\n" +
                                    "250 HELP");

                        } else {
                            return LIHelper.vectorFromString("501 Syntax error in parameters or arguments");
                        }
                    }

                    if (msg.contains("AUTH") && msg.contains("LOGIN")) {
                        state = auth;
                        return LIHelper.vectorFromString("334 VXNlcm5hbWU6");
                    }

                case auth:
                    state = authdone;
                    return LIHelper.vectorFromString("334 UGFzc3dvcmQ6");

                case authdone:
                    state = login;
                    return LIHelper.vectorFromString("235 Authentication succeeded");
                case login:

                    if(messageFromClient.contains("mail from:")){
                        emailfrom = msg.get(3);
                        return LIHelper.vectorFromString("250 OK");
                    }

                    if(messageFromClient.contains("rcpt to:")){
                        emailfrom = msg.get(3);
                        return LIHelper.vectorFromString("250 Accepted");
                    }

                    if(messageFromClient.contains("data")) {

                        state = sendmail;
                        return LIHelper.vectorFromString("354 Enter message, ending with '.' on a line by itself ");
                    }


                    return LIHelper.vectorFromString("235 Authentication succeeded");

                case sendmail:

                        if(messageFromClient.equals(".")){
                            state = login;
                            return LIHelper.vectorFromString("250 OK");
                        }else {
                            return LIHelper.vectorFromString("");
                        }


            }

        }else {

            return LIHelper.vectorFromString("220-smtp."+ domain +" ESMTP Exim 4.66");
        }
        return LIHelper.vectorFromString("500 Syntax error, command unrecognised");
    }

    @Override
    public int getPort() {
        return 25;
    }

    @Override
    public boolean isConnectionOver() {
        return false;
    }

    public String toString(){
        return "SMTP";
    }
}
