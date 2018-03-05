package HoneyPot.protocol;

import HoneyPot.lowinteraction.LIHelper;
import HoneyPot.lowinteraction.LIProtocol;
import HoneyPot.lowinteraction.TALK_FIRST;

import java.util.Date;
import java.util.PrimitiveIterator;
import java.util.Vector;

public class MySQLProtocol implements LIProtocol
{




    private String Service = "mysql";


    private int state = 0;

    private final int login = 1;

    private final int killed = 666;
    @Override
    public TALK_FIRST whoTalksFirst() {
        return TALK_FIRST.SVR_FIRST;
    }
    @Override
    public Vector<String> processInput(String messageFromClient) {


        if(messageFromClient != null) {
            if (messageFromClient.toLowerCase().equals("help")) {
                return LIHelper.vectorFromString("Note that all text commands must be first on line and end with ';'\n" +
                        "?         (\\?) Synonym for `help'.\n" +
                        "clear     (\\c) Clear the current input statement.\n" +
                        "connect   (\\r) Reconnect to the server. Optional arguments are db and host.\n" +
                        "delimiter (\\d) Set statement delimiter.\n" +
                        "ego       (\\G) Send command to mysql server, display result vertically.\n" +
                        "exit      (\\q) Exit mysql. Same as quit.\n" +
                        "go        (\\g) Send command to mysql server.\n" +
                        "help      (\\h) Display this help.\n" +
                        "notee     (\\t) Don't write into outfile.\n" +
                        "print     (\\p) Print current command.\n" +
                        "prompt    (\\R) Change your mysql prompt.\n" +
                        "quit      (\\q) Quit mysql.\n" +
                        "rehash    (\\#) Rebuild completion hash.\n" +
                        "source    (\\.) Execute an SQL script file. Takes a file name as an argument.\n" +
                        "status    (\\s) Get status information from the server.\n" +
                        "tee       (\\T) Set outfile [to_outfile]. Append everything into given outfile.\n" +
                        "use       (\\u) Use another database. Takes database name as argument.\n" +
                        "charset   (\\C) Switch to another charset. Might be needed for processing binlog with multi-byte charsets.\n" +
                        "warnings  (\\W) Show warnings after every statement.\n" +
                        "nowarning (\\w) Don't show warnings after every statement.\n" +
                        "resetconnection(\\x) Clean session context.\n" +
                        "\n" +
                        "For server side help, type 'help contents'");
            }

            if(messageFromClient.toLowerCase().contains("ver")){
                return LIHelper.vectorFromString("5.17.34");

            }
        }



        switch(state){

            case 0:
                state = 2;
                return LIHelper.vectorFromString("5.7.20");
                //return LIHelper.vectorFromString("5.7.21-log \u000E   3.}0\f0r^  ≈!\u0002  ü\u0015          6[\f\"\u0005\"0wi@15 mysql_native_password" + new Date().toString());


            case 2:
                if(messageFromClient.equals("    ")){
                    state = killed;
                    return LIHelper.vectorFromString("5.3.2");
                }
            case killed:
                return LIHelper.vectorFromString("Aborted_connects");




        }
        return null;
    }

    @Override
    public int getPort() {
        return 3306;
    }

    @Override
    public boolean isConnectionOver() {
        return state == killed;
    }
}
