package HoneyPot.Repository;

import HoneyPot.logging.LogConnection;
import Model.User;

import java.util.Set;

public interface ILogSerialisation {
    void SaveLog(LogConnection log, User user);
    void SaveLogs(Iterable<LogConnection> logs, User user);
    Set<LogConnection> GetAllLogs();
}
