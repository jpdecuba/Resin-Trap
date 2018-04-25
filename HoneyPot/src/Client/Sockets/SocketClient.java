package Client.Sockets;

import Client.Model.User;
import Shared.Mail.MailMsg;
import Shared.Request.Request;
import Shared.Request.RequestType;
import com.sun.scenario.Settings;

import javax.net.SocketFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class SocketClient {
    private java.net.Socket Socket;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    public SocketClient(java.net.Socket socket) {
        if(socket != null) {
            Socket = socket;
        }else {
            SocketFactory socketFactory = (SocketFactory) SocketFactory.getDefault();
            try {
                this.Socket = (Socket) socketFactory.createSocket("localhost", 7676);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            output = new ObjectOutputStream(Socket.getOutputStream());

            BufferedInputStream socketRead = new BufferedInputStream(Socket.getInputStream());

            input = new ObjectInputStream(socketRead);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public boolean SendEmail(MailMsg msg){

        try {
            Request RequestSets = new Request(RequestType.Mail,msg);
            output.writeObject(RequestSets);
            Object obj = input.readObject();
            if(obj instanceof Boolean) {
                boolean results = ((boolean) obj);
                output.flush();
                return results;
            }

        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            return false;
        }



        return false;
    }

    public boolean Register(User usr){

        try {
            Request RequestSets = new Request(RequestType.Register,usr);
            output.writeObject(RequestSets);
            Object obj = input.readObject();
            if(obj instanceof Boolean) {
                boolean results = ((boolean) obj);
                output.flush();
                return results;
            }

        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            return false;
        }



        return false;
    }

    public boolean RegisterAdmin(User usr){

        try {
            Request RequestSets = new Request(RequestType.RegisterAdmin,usr);
            output.writeObject(RequestSets);
            Object obj = input.readObject();
            if(obj instanceof Boolean) {
                boolean results = ((boolean) obj);
                output.flush();
                return results;
            }

        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            return false;
        }



        return false;
    }

    public void flush(){

        try {
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void close(){
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //input.close();
    }



}
