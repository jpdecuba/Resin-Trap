package netwerkscan;

import HoneyPot.lowinteraction.LIModule;
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
}
