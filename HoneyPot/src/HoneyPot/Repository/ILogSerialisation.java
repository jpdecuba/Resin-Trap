package HoneyPot.Repository;

import HoneyPot.logging.LogConnection;
import Model.User;

public interface ILogSerialisation {
    void SaveLog(LogConnection log, User user);
    void SaveLogs(Iterable<LogConnection> logs, User user);
    Iterable<LogConnection> GetAllLogsForService(String service);
}
