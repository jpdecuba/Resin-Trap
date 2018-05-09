package Shared.Request;

import Client.HoneyPot.logging.LogConnection;
import Client.Model.User;
import Shared.Mail.MailMsg;

import java.io.Serializable;

public class Request implements Serializable{

    private RequestType msg;

    private MailMsg email;

    private User Account;

    private LogConnection log;
    private Iterable<LogConnection> logs;

    private int id;
    private String emailAccount;

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

    public Request(RequestType msg, User Account, LogConnection log) {
        this.msg = msg;
        this.Account = Account;
        this.log = log;
    }

    public Request(RequestType msg, User Account, Iterable<LogConnection> logs) {
        this.msg = msg;
        this.Account = Account;
        this.logs = logs;

    }

    public Request(RequestType msg, String emailAccount, int id)
    {
        this.msg = msg;
        this.emailAccount = emailAccount;
        this.id = id;
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

    public int getId() { return id; }

    public String getEmailAccount() { return emailAccount; }

    public LogConnection getLog() {
        return log;
    }

    public Iterable<LogConnection> getLogs() {
        return logs;
    }
}
