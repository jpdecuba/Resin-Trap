package Client.netwerkscan;

import Client.HoneyPot.lowinteraction.LIModule;
import Client.HoneyPot.lowinteraction.LIProtocol;
import Client.HoneyPot.protocol.*;
import Client.Main.Main;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.List;


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
        MysqlM = new LIModule(MysqlP, Main.honeypot);
        SmtpM = new LIModule(SmtpP, Main.honeypot);
        ftpM = new LIModule(ftpP, Main.honeypot);
        LIProtocol ircP = new IrcProtocol();
        LIModule ircM = new LIModule(ircP, Main.honeypot);


        blankm = new LIModule(blank, Main.honeypot);


        Services.add(blankm);
        Services.add(MysqlM);
        Services.add(SmtpM);
        Services.add(ftpM);
        Services.add(ircM);


        /*PortScanner scan = new PortScanner();
        System.out.println(" all: " + scan.ALLPortScan("localhost"));
        System.out.println("quick:" + scan.QuickScan("localhost"));*/

        PresetsScan presets = new PresetsScan();

        System.out.println(presets.checkHosts());



        List<LIModule> result = presets.QuickScan("Localhost",Services);
        System.out.println("quick " + result);
        List<LIModule> result2 = presets.Totalscan("Localhost",Services);
        System.out.println("Total " +result2);

        List<LIModule> result3 = presets.QuickScanblank("Localhost",Services, Main.honeypot);
        System.out.println("quickblank " + result3);
        List<LIModule> result4 = presets.TotalScanblank("Localhost",Services, Main.honeypot);
        System.out.println("Totalblank " + result4);
    }

}
