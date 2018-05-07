package Server.Database.LogDB.Repository;

import Client.HoneyPot.logging.LogConnection;
import Client.Model.User;

import java.util.Set;

public class LogModel {

    private LogRepository LogDB;

    public LogModel() {
        LogDB = new LogRepository(new LogDatabase());
    }

    public void SaveLog(LogConnection log, User user){
        LogDB.SaveLog(log,user);
    }
    public void SaveLogs(Iterable<LogConnection> logs, User user){
        LogDB.SaveLogs(logs,user);
    }
    public Set<LogConnection> GetAllLogs(){
        return  LogDB.GetAllLogs();
    }
}
