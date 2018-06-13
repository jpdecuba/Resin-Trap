package Client.Sockets;

import Shared.Logging.LogConnection;
import Shared.Mail.MailMsg;
import Shared.Model.User;
import Shared.Model.UserRole;
import Shared.Request.Request;
import Shared.Request.RequestType;

import javax.net.SocketFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Set;

public class LogSocket {
    private java.net.Socket Socket;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    private int failedAttempt = 0;

    private SocketFactory socketFactory = (SocketFactory) SocketFactory.getDefault();

    private SocketObserver observer;

    private ResponseThread server;

    //188.166.118.138
    public LogSocket(SocketObserver observer) {
        this.observer = observer;

            try {
                this.Socket = (java.net.Socket) socketFactory.createSocket("188.166.118.138", 7677);
            } catch (IOException e) {
            }


        try {
            if(SocketCheck()) {
                output = new ObjectOutputStream(Socket.getOutputStream());

                BufferedInputStream socketRead = new BufferedInputStream(Socket.getInputStream());

                input = new ObjectInputStream(socketRead);

                StartServerThread();
            }
        } catch (IOException e) {

        }

    }


    public boolean SocketCheck(){
        if(this.Socket != null) {
            if(this.Socket.isConnected()){
                return true;
            }else {
                ReConnect();
                return this.Socket.isConnected();
            }
        }
        return ReConnect();
    }


    public void SaveLogs(User usr, Iterable<LogConnection> logs){
        try {
            if(SocketCheck()) {
                Request RequestSets = new Request(RequestType.SaveLogs, usr,logs);
                output.writeObject(RequestSets);
            }
        } catch (SocketException e){
            if (failedAttempt < 5) {
                ReConnect();
                SaveLogs(usr, logs);
            }else {
                failedAttempt = 0;
            }
        }catch (IOException e) {
            //e.printStackTrace();
        }

    }

    public void SaveLog(User usr, LogConnection log){
        try {
            if(SocketCheck()) {
                Request RequestSets = new Request(RequestType.SaveLog, usr, log);
                output.writeObject(RequestSets);
            }
        } catch (SocketException e) {
            if (failedAttempt < 5){
                ReConnect();
                SaveLog(usr,log);
            }else {
                failedAttempt = 0;
            }

        }catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void GetlogFiles(User usr){
        if(usr != null) {
            if (usr.getRole().equals(UserRole.Admin)) {
                //Edit to admin get logs
                GetLogsAdmin(usr);
            } else if (usr.getRole().equals(UserRole.User)) {
                GetLogs(usr);
            }
        }
    }



    private void GetLogs(User usr){

        try {
            if(SocketCheck()) {
                Request RequestSets = new Request(RequestType.GetLogs, usr);
                output.writeObject(RequestSets);
                }


        }catch (SocketException e) {
            if (failedAttempt < 5){
                ReConnect();
                GetLogs(usr);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void GetLogsAdmin(User usr){

        try {
            if(SocketCheck()) {
                Request RequestSets = new Request(RequestType.GetLogsAdmin, usr);
                output.writeObject(RequestSets);
            }
        }catch (SocketException e) {
            if (failedAttempt < 5){
                ReConnect();
                GetLogsAdmin(usr);
            }else {
                failedAttempt = 0;
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean ReConnect() {
        failedAttempt++;
        try {
            this.Socket = (Socket) socketFactory.createSocket("188.166.118.138", 7676);

            output = new ObjectOutputStream(Socket.getOutputStream());

            BufferedInputStream socketRead = new BufferedInputStream(Socket.getInputStream());

            input = new ObjectInputStream(socketRead);

            StartServerThread();

            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public void StartServerThread(){
            server = new ResponseThread(input,observer);
            server.start();

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
