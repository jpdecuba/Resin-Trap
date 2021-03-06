package Server;

import Shared.Logging.LogConnection;
import Shared.Model.User;
import Server.Repositories.LogModel;
import Server.Repositories.LoginModel;
import Server.Mail.EmailSend;
import Shared.Request.Request;
import Shared.Request.RequestType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Set;

public class ServerHandler {

    private java.net.Socket Socket;
    private ObjectOutputStream output;

    public ServerHandler(Socket socket,ObjectOutputStream out ) {
        this.Socket = socket;
        this.output = out;
    }

    public void Handler(Request object){

        LoginModel LM = new LoginModel();
        try {
            RequestType msg = object.getMsg();
            LogModel LogM = new LogModel();
            switch (msg){
                case Mail:
                    EmailSend send = new EmailSend();
                    boolean mail = send.SendEmail(object.Getemail());
                    output.writeObject(mail);
                    break;
                case Login:
                    User u =LM.Login(object.getAccount());
                    if (u != null) {
                        u.setToken(getSaltString());
                        BackEndServer.sessions.add(u);
                        output.writeObject(u);
                    }else {
                        output.writeObject(null);
                    }

                    break;
                case Logout:
                    BackEndServer.sessions.removeIf(x -> x.getName().equals(object.getAccount().getName()) && x.getToken().equals(object.getAccount().getToken()));
                    output.writeObject(true);
                    break;
                case Register:
                    boolean register = LM.Register(object.getAccount());
                    output.writeObject(register);
                    break;
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
                case AddMail:
                    boolean added = LM.AddEmail(object.getAttachment(), object.getId());
                    output.writeObject(added);
                    break;
                case ChangePassword:
                    boolean changed = LM.ChangePassword(object.getAttachment(), object.getId());
                    output.writeObject(changed);
                    break;
                case DeleteEmail:
                    boolean deleted = LM.DeleteEmail(object.getAttachment(), object.getId());
                    output.writeObject(deleted);
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