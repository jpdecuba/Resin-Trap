package Client.netwerkscan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PortScanner {

    private ExecutorService exec = null;



    private void start(){
        exec = Executors.newFixedThreadPool(1000);
    }

    private void stop(){
        exec.shutdown();
    }


    public ArrayList<Integer> ALLPortScan(String ipadress){
        start();
        ArrayList<Integer> list = new ArrayList<>();
        List<Future<Integer>> futures = new ArrayList<Future<Integer>>();


        for (int port = 1; port <= 65535; port++) {
            Callable worker = new ScanThread(ipadress,port);
            Future<Integer> f = exec.submit(worker);
            futures.add(f);
        }
        for(Future<?> future : futures) {
            try {
                if(future.get() != null) {
                    list.add((Integer) future.get());
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
            } catch (ExecutionException e) {
                //e.printStackTrace();
            }
        }
        stop();
        return list;
    }


    public ArrayList<Integer> QuickScan(String ipadress) {
        start();
        ArrayList<Integer> list = new ArrayList<>();
        List<Future<Integer>> futures = new ArrayList<Future<Integer>>();

        for (int port = 1; port <= 1000; port++) {
            Callable worker = new ScanThread(ipadress,port);
            Future<Integer> f = exec.submit(worker);
            futures.add(f);
        }

        for(Future<?> future : futures) {
            try {
                if(future.get() != null) {
                    list.add((Integer) future.get());
                }
            } catch (InterruptedException e) {

            } catch (ExecutionException e) {

            }
        }
        stop();
        return list;
    }




}
