package Server.Repositories.Repository;

import Client.HoneyPot.logging.LogConnection;
import Client.Model.User;
import Server.Repositories.Interface.ILogSerialisation;

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
    public Set<LogConnection> GetLogsByUser(User usr) {
        return context.GetLogsByUser(usr);
    }

    @Override
    public Set<LogConnection> GetLogsAdmin(User usr) { return context.GetLogsAdmin(usr); }
}