package HoneyPot.Repository;

import HoneyPot.logging.LogConnection;
import Model.User;

public class LogRepository implements ILogSerialisation{

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
    public Iterable<LogConnection> GetAllLogsForService(String service) {
        return null;
    }
}
