package netwerkscan;

import HoneyPot.honeyrj.HoneyRJ;
import HoneyPot.lowinteraction.LIModule;
import HoneyPot.protocol.BlankProtocol;
import com.sun.corba.se.impl.orbutil.concurrent.Sync;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class PresetsScan {

    private int count = 0;

    private boolean bool = true;
    private  int boolcount = 0;

    private ExecutorService exec = null;

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

    public int checkHosts(){
        exec = Executors.newCachedThreadPool();
        List<Future<Integer>> futures = new ArrayList<Future<Integer>>();
        count = 0;
        boolcount = 0;
        bool = true;
        try {
            InetAddress localHost = Inet4Address.getLocalHost();
            String ip = localHost.getHostAddress();
            String[] parts = ip.split("\\.");
            final String substring = parts[0] + "." +  parts[1] + "." + parts[2];

        for (int i=1;i<255;i++){

            String host = substring + "." + i;

            Callable worker = new NetwerkScanThread(host);
            Future<Integer> f = exec.submit(worker);
            futures.add(f);
            }
        }catch (IOException e) {

        }finally {
            for(Future<?> future : futures) {
                try {
                    if(future.get() != null) {
                        count = count +(Integer) future.get();
                    }
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                } catch (ExecutionException e) {
                    //e.printStackTrace();
                }
            }

            synchronized (this) {
                return count;
            }
        }

    }




}
