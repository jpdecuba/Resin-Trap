package Client.Model.Repositories.Interface;

import Client.Model.User;
import Client.HoneyPot.logging.LogConnection;


import java.util.Set;

public interface ILogSerialisation {
    void SaveLog(LogConnection log, User user);
    void SaveLogs(Iterable<LogConnection> logs, User user);
    Set<LogConnection> GetAllLogs();
    Set<LogConnection> GetLogsByUser();
}