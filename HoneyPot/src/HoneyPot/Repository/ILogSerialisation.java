package HoneyPot.Repository;

import HoneyPot.logging.LogConnection;

public interface ILogSerialisation extends IRepository<LogConnection>{
    Iterable<LogConnection> GetAllLogsForService(String service);
}
