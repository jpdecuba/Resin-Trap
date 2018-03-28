package netwerkscan;

import HoneyPot.honeyrj.HoneyRJ;
import HoneyPot.lowinteraction.LIModule;
import HoneyPot.protocol.BlankProtocol;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.ArrayList;
import java.util.List;

public class PresetsScan {

    public List<LIModule> QuickScan(String ip, List<LIModule> modules){
        PortScanner scan = new PortScanner();
        List<Integer> openports = scan.QuickScan(ip);
        return modulescheck(openports, modules);
    }



    public List<LIModule> Totalscan(String ip, List<LIModule> modules){
        PortScanner scan = new PortScanner();
        List<Integer> openports = scan.ALLPortScan(ip);
        return modulescheck(openports, modules);
    }
    public List<LIModule> QuickScanblank(String ip, List<LIModule> modules,HoneyRJ honeyrj){
        PortScanner scan = new PortScanner();
        List<Integer> openports = scan.QuickScan(ip);
        return modulescheckBlank(openports, modules,honeyrj);
    }

    public List<LIModule> TotalScanblank(String ip, List<LIModule> modules,HoneyRJ honeyrj){
        PortScanner scan = new PortScanner();
        List<Integer> openports = scan.ALLPortScan(ip);
        return modulescheckBlank(openports, modules,honeyrj);
    }

    private List<LIModule> modulescheck(List<Integer> openports, List<LIModule> modules){
        List<LIModule> returnlist = new ArrayList<>();

        for(Integer i : openports){
            for(LIModule item : modules){

                if(item.getPort() == i){
                    if(!returnlist.contains(item)) {
                        returnlist.add(item);
                    }
                }
            }
        }
        return returnlist;
    }


    private List<LIModule> modulescheckBlank(List<Integer> openports, List<LIModule> modules, HoneyRJ honeyrj){
        List<LIModule> returnlist = new ArrayList<>();
        int q = 0;
        for(Integer i : openports){
            q = i;
            for(LIModule item : modules){

                if(item.getPort() == i){
                    if(!returnlist.contains(item)) {
                        returnlist.add(item);
                        q = 0;
                    }
                }
            }
            if(q != 0){
                BlankProtocol blank = new BlankProtocol(q);
                LIModule blankm = new LIModule(blank,honeyrj);
                returnlist.add(blankm);
            }
        }
        return returnlist;
    }
}
