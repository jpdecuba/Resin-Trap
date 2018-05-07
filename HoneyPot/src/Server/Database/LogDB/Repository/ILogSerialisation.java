package Server.Database.LogDB.Repository;

import Client.HoneyPot.logging.LogConnection;
import Client.Model.User;

import java.util.Set;

public interface ILogSerialisation {
    void SaveLog(LogConnection log, User user);
    void SaveLogs(Iterable<LogConnection> logs, User user);
    Set<LogConnection> GetAllLogs();

    Set<LogConnection> GetLogs(User usr);
}