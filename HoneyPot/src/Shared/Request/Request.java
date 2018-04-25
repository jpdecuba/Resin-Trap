package Shared.Request;

import Client.Model.User;
import Shared.Mail.MailMsg;

import java.io.Serializable;

public class Request implements Serializable{

    private RequestType msg;

    private MailMsg email;

    private User Account;



    public Request(RequestType msg) {
        this.msg = msg;
    }

    public Request(RequestType msg, MailMsg email) {
        this.msg = msg;
        this.email = email;
    }

    public Request(RequestType msg, User Account) {
        this.msg = msg;
        this.Account = Account;
    }



    public RequestType getMsg() {
        return msg;
    }

    public MailMsg Getemail(){
        return email;
    }

    public User getAccount() {
        return Account;
    }
}
