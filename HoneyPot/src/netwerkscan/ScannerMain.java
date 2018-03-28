package netwerkscan;

import HoneyPot.lowinteraction.LIModule;
import HoneyPot.lowinteraction.LIProtocol;
import HoneyPot.protocol.*;

import javax.xml.bind.DatatypeConverter;
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
        LIProtocol ircP = new IrcProtocol();
        LIModule ircM = new LIModule(ircP,honeypot);


        blankm = new LIModule(blank,honeypot);


        Services.add(blankm);
        Services.add(MysqlM);
        Services.add(SmtpM);
        Services.add(ftpM);
        Services.add(ircM);


        /*PortScanner scan = new PortScanner();
        System.out.println(" all: " + scan.ALLPortScan("localhost"));
        System.out.println("quick:" + scan.QuickScan("localhost"));*/

        PresetsScan presets = new PresetsScan();



        List<LIModule> result = presets.QuickScan("Localhost",Services);
        System.out.println("quick " + result);
        List<LIModule> result2 = presets.Totalscan("Localhost",Services);
        System.out.println("Total " +result2);

        List<LIModule> result3 = presets.QuickScanblank("Localhost",Services,honeypot);
        System.out.println("quickblank " + result3);
        List<LIModule> result4 = presets.TotalScanblank("Localhost",Services,honeypot);
        System.out.println("Totalblank " + result4);
    }

}
