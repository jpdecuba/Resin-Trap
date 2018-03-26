package netwerkscan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PortScanner {


    private ExecutorService exec = Executors.newFixedThreadPool(1000);
    //private ExecutorService exec = Executors.newCachedThreadPool();


    public ArrayList<Integer> ALLPortScan(String ipadress){

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
        exec.shutdown();
        return list;
    }


    public ArrayList<Integer> QuickScan(String ipadress) {

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
        exec.shutdown();
        return list;





    }




}
