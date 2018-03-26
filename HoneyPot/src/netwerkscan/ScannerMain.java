package netwerkscan;

public class ScannerMain {

    public static void main(String[] args){

        PortScanner scan = new PortScanner();

        System.out.println(scan.QuickScan("localhost"));

        System.out.println(scan.ALLPortScan("localhost"));
    }

}
