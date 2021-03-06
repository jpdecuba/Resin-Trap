package Server.LogServer;

import Server.Mail.EmailSend;
import Server.Repositories.LogModel;
import Server.Repositories.LoginModel;
import Shared.Logging.LogConnection;
import Shared.Model.User;
import Shared.Request.Request;
import Shared.Request.RequestType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Set;

public class ServerHandlerLogs {

    private java.net.Socket Socket;
    private ObjectOutputStream output;

    public ServerHandlerLogs(Socket socket, ObjectOutputStream out ) {
        this.Socket = socket;
        this.output = out;
    }

    public void Handler(Request object){

        try {
            RequestType msg = object.getMsg();
            LogModel LogM = new LogModel();
            switch (msg){
                case GetAllLogs:
                    output.writeObject(LogM.GetAllLogs());
                    break;
                case SaveLog:
                    LogM.SaveLog(object.getLog(),object.getAccount());
                    break;
                case SaveLogs:
                    LogM.SaveLogs(object.getLogs(),object.getAccount());
                    break;
                case GetLogs:
                    Set<LogConnection> logs = LogM.GetLogsByUser(object.getAccount());
                    output.writeObject(logs);
                    break;
                case GetLogsAdmin:
                    Set<LogConnection> logs2 = LogM.GetLogsAdmin(object.getAccount());
                    output.writeObject(logs2);
                    break;
            }
            output.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }







}