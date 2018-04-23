package HoneyPot.protocol;

import Client.HoneyPot.lowinteraction.LIHelper;
import Client.HoneyPot.lowinteraction.TALK_FIRST;
import Client.HoneyPot.protocol.MySQLProtocol;

import java.util.Vector;

import static org.junit.Assert.*;

public class MySQLProtocolTest {

    private MySQLProtocol proto;

    @org.junit.Before
    public void setUp() throws Exception {

        proto = new MySQLProtocol();
    }

    @org.junit.Test
    public void whoTalksFirst() {
        assertEquals(TALK_FIRST.SVR_FIRST,proto.whoTalksFirst());
    }

    @org.junit.Test
    public void processInput() {
        Vector<String> result = LIHelper.vectorFromString("Note that all text commands must be first on line and end with ';'\n" +
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
        assertEquals(result,proto.processInput("help"));

    }

    @org.junit.Test
    public void getPort() {
        assertEquals(3306,proto.getPort());
    }

    @org.junit.Test
    public void Stringtest() {
        assertEquals("mySQL",proto.toString());
    }
}