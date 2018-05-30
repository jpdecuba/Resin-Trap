package Shared.Request;

import Shared.Logging.LogConnection;
import Shared.Model.User;

import java.io.Serializable;
import java.util.Set;

public class Response implements Serializable {

    private boolean bool;

    private ResponseType response;

    private User user;

    private Set<LogConnection> logs;

    public Response(ResponseType response, boolean bool) {
        this.bool = bool;
        this.response = response;
    }

    public Response(ResponseType response, User user) {
        this.response = response;
        this.user = user;
    }


    public Response(ResponseType response, Set<LogConnection> logs) {
        this.response = response;
        this.logs = logs;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public ResponseType getResponse() {
        return response;
    }

    public void setResponse(ResponseType response) {
        this.response = response;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<LogConnection> getLogs() {
        return logs;
    }

    public void setLogs(Set<LogConnection> logs) {
        this.logs = logs;
    }
}
