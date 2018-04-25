package Server;

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

    public void DBGet(Request object){


        try {
            //ObjectOutputStream output = new ObjectOutputStream(Socket.getOutputStream());

            RequestType msg = object.getMsg();

            switch (msg){
                case Mail:
                    EmailSend send = new EmailSend();
                    boolean mail = send.SendEmail(object.Getemail());
                    output.writeUTF(String.valueOf(mail));
                    break;
            }
            output.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }









}