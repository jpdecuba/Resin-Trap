package Server.Repositories;

import Shared.Logging.LogConnection;
import Shared.Model.User;
import Server.Repositories.Database.LogDatabase;
import Server.Repositories.Repository.LogRepository;

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

    public Set<LogConnection> GetLogsByUser(User usr) {
        return LogDB.GetLogsByUser(usr);
    }
    public Set<LogConnection> GetLogsAdmin(User usr) {return LogDB.GetLogsAdmin(usr);}
}
