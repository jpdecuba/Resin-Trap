package Server;

import Shared.Request.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BackEndThread extends Thread{
    protected Socket socket;

    private ServerHandler handler;

    public BackEndThread(Socket clientSocket) {
        this.socket =  clientSocket;

    }



    public void run() {


        try {
            System.err.println("Connecting started");
            // Data streams
            ObjectInputStream input;
            ObjectOutputStream output;


            output = new ObjectOutputStream (socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());



            this.handler = new ServerHandler(socket,output);

            while (socket.isConnected()) {

                System.out.println("in loop");

                Object msg = input.readObject();
                if(msg.getClass() == Request.class){

                    Request item = (Request) msg;
                    handler.Handler(item);

                }else {
                    output.writeUTF("No valid request");

                }
                if (socket.isClosed()) {
                    output.close();
                    input.close();

                }


            }
            output.close();
            input.close();




            socket.close();

        } catch (IOException ioException) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ioException.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}