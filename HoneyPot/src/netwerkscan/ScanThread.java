package netwerkscan;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ScanThread  implements Callable<Integer> {
    private String ipadress;
    private int port;


    public ScanThread(String ipadress, int port) {
        this.ipadress = ipadress;
        this.port = port;
    }

    public Integer scan() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ipadress, port), 1000);
            socket.close();
        return port;
        } catch (IOException e) {

        }
        return null;

    }

    @Override
    public Integer call() throws Exception {
        return scan();
    }
}
