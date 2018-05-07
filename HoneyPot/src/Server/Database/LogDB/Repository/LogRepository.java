package Server.Database.LogDB.Repository;

import Client.HoneyPot.logging.LogConnection;
import Client.Model.User;

import java.util.Set;

public class LogRepository implements ILogSerialisation {

    private ILogSerialisation context;
    public LogRepository(ILogSerialisation context){ this.context = context; }

    @Override
    public void SaveLog(LogConnection log, User user) {
        context.SaveLog(log,user);
    }

    @Override
    public void SaveLogs(Iterable<LogConnection> logs, User user) {
        context.SaveLogs(logs, user);
    }



    @Override
    public Set<LogConnection> GetAllLogs() {
        return context.GetAllLogs();
    }

    @Override
    public Set<LogConnection> GetLogs(User usr) {
        return context.GetLogs(usr);
    }
}