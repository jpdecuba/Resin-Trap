package netwerkscan;

import HoneyPot.lowinteraction.LIModule;
import HoneyPot.lowinteraction.LIProtocol;
import HoneyPot.protocol.BlankProtocol;
import HoneyPot.protocol.FtpProtocol;
import HoneyPot.protocol.MySQLProtocol;
import HoneyPot.protocol.SmtpProtocol;

import java.util.ArrayList;
import java.util.List;

import static Main.Main.honeypot;

public class ScannerMain {

    public static ArrayList<LIModule> Services = new ArrayList<>();

    private static LIProtocol ftpP = new FtpProtocol();
    public static LIModule ftpM;
    private static LIProtocol SmtpP = new SmtpProtocol();
    private static LIModule SmtpM;
    private static LIProtocol MysqlP = new MySQLProtocol();
    private static LIModule MysqlM;

    private static LIProtocol blank = new BlankProtocol(4444);
    private static LIModule blankm;

    public static void main(String[] args){
        MysqlM = new LIModule(MysqlP,honeypot);
        SmtpM = new LIModule(SmtpP,honeypot);
        ftpM = new LIModule(ftpP,honeypot);

        blankm = new LIModule(blank,honeypot);


        Services.add(blankm);
        Services.add(MysqlM);
        Services.add(SmtpM);
        Services.add(ftpM);


        /*PortScanner scan = new PortScanner();
        System.out.println(" all: " + scan.ALLPortScan("localhost"));
        System.out.println("quick:" + scan.QuickScan("localhost"));*/

        PresetsScan presets = new PresetsScan();

        List<LIModule> result = presets.QuickScan("Localhost",Services);

        System.out.println(result);




    }

}
