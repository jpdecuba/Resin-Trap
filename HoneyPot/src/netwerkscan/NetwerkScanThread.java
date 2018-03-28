package netwerkscan;

import Model.Preset.Preset;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.Callable;

public class NetwerkScanThread implements Callable<Integer> {


    private String host;

    public NetwerkScanThread(String host) {
        this.host = host;

    }



    @Override
    public Integer call() throws Exception {
        try {
            if (InetAddress.getByName(host).isReachable(100)){

                return 1;
            }
        } catch (IOException e) {

        }
        return 0;
    }
}
