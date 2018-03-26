package netwerkscan;

public class ScannerMain {

    public static void main(String[] args){

        PortScanner scan = new PortScanner();
        System.out.println(" all: " + scan.ALLPortScan("localhost"));
        System.out.println("quick:" + scan.QuickScan("localhost"));


    }

}
