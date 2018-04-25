package Shared.Request;

import Shared.Mail.MailMsg;

import java.io.Serializable;

public class Request implements Serializable{

    private RequestType msg;

    private MailMsg email;



    public Request(RequestType msg) {
        this.msg = msg;
    }

    public Request(RequestType msg, MailMsg email) {
        this.msg = msg;
        this.email = email;
    }



    public RequestType getMsg() {
        return msg;
    }

    public MailMsg Getemail(){
        return email;
    }
}
