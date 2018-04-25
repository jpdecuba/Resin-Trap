package Server;

import Client.Model.Database.Database.Database;
import Server.Database.Database.UserDatabase;
import Server.Database.LoginModel;
import Server.Mail.EmailSend;
import Shared.Request.Request;
import Shared.Request.RequestType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Set;

import static Shared.Request.RequestType.Mail;

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
            switch (msg){
                case Mail:
                    EmailSend send = new EmailSend();
                    boolean mail = send.SendEmail(object.Getemail());
                    output.writeObject(mail);
                    break;
                case Register:
                    boolean register =LM.Register(object.getAccount());
                    output.writeObject(register);
                    break;
                case RegisterAdmin:
                    boolean registeradmin = LM.RegisterAdmin(object.getAccount());
                    output.writeObject(registeradmin);
                    break;
            }
            output.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }









}