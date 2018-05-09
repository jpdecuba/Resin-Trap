package Client.Sockets;

import Client.HoneyPot.logging.LogConnection;
import Client.Model.User;
import Client.Model.UserRole;
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
import java.util.Set;

public class SocketClient {
    private java.net.Socket Socket;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    //188.166.118.138
    public SocketClient(java.net.Socket socket) {
        if(socket != null) {
            Socket = socket;
        }else {
            SocketFactory socketFactory = (SocketFactory) SocketFactory.getDefault();
            try {
                this.Socket = (Socket) socketFactory.createSocket("188.166.118.138", 7676);
            } catch (IOException e) {
            }
        }

        try {
            if(SocketCheck()) {
                output = new ObjectOutputStream(Socket.getOutputStream());

                BufferedInputStream socketRead = new BufferedInputStream(Socket.getInputStream());

                input = new ObjectInputStream(socketRead);
            }
        } catch (IOException e) {

        }

    }


    public boolean SocketCheck(){
        if(this.Socket != null) {
            return true;
        }
        return false;
    }


    public boolean SendEmail(MailMsg msg){
        try {
            if(SocketCheck()) {
                Request RequestSets = new Request(RequestType.Mail, msg);
                output.writeObject(RequestSets);
                Object obj = input.readObject();
                if (obj instanceof Boolean) {
                    boolean results = ((boolean) obj);
                    output.flush();
                    return results;
                }
            }else{
                return false;
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

    public User Login(User usr){

        try {
            if(SocketCheck()) {
                Request RequestSets = new Request(RequestType.Login, usr);
                output.writeObject(RequestSets);
                Object obj = input.readObject();
                if (obj instanceof User) {
                    User results = ((User) obj);
                    output.flush();
                    return results;
                }
            }else {
                return null;
            }

        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            return null;
        }
        return null;
    }

    public boolean Logout(User usr){
        try {
            if(SocketCheck()) {
                Request RequestSets = new Request(RequestType.Logout, usr);
                output.writeObject(RequestSets);
                Object obj = input.readObject();
                if (obj instanceof Boolean) {
                    boolean results = ((boolean) obj);
                    output.flush();
                    return results;
                }
            }else {
                return false;
            }
        } catch (IOException e) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
        return false;
    }

    public boolean Register(User usr){

        try {
            if(SocketCheck()) {
                Request RequestSets = new Request(RequestType.Register, usr);
                output.writeObject(RequestSets);
                Object obj = input.readObject();
                if (obj instanceof Boolean) {
                    boolean results = ((boolean) obj);
                    output.flush();
                    return results;
                }
            }else {
                return false;
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
            if(SocketCheck()) {
                Request RequestSets = new Request(RequestType.RegisterAdmin, usr);
                output.writeObject(RequestSets);
                Object obj = input.readObject();
                if (obj instanceof Boolean) {
                    boolean results = ((boolean) obj);
                    output.flush();
                    return results;
                }
            }
            return false;

        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            return false;
        }
    }

    public void SaveLogs(User usr, Iterable<LogConnection> logs){

        try {
            if(SocketCheck()) {
                Request RequestSets = new Request(RequestType.SaveLogs, usr,logs);
                output.writeObject(RequestSets);
            }

        } catch (IOException e) {
            //e.printStackTrace();
        }

    }

    public void SaveLog(User usr, LogConnection log){

        try {
            if(SocketCheck()) {
                Request RequestSets = new Request(RequestType.SaveLog, usr, log);
                output.writeObject(RequestSets);
            }

        } catch (IOException e) {
            //e.printStackTrace();
        }


    }


    public Set<LogConnection> GetlogFiles(User usr){
        if(usr.getRole().equals(UserRole.Admin)){
            //Edit to admin get logs
            return GetLogs(usr);
        }else if(usr.getRole().equals(UserRole.User)){
            return GetLogs(usr);
        }

        return null;
    }



    private Set<LogConnection> GetLogs(User usr){

        try {
            if(SocketCheck()) {
                Request RequestSets = new Request(RequestType.GetLogs, usr);
                output.writeObject(RequestSets);
                Object obj = input.readObject();
                    Set<LogConnection> results = ((Set<LogConnection>) obj);
                    output.flush();
                    return results;

            }
            return null;

        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            return null;
        }
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
