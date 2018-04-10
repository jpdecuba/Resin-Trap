package HoneyPot.Repository;

import HoneyPot.logging.LogConnection;

public class LogRepository extends Repository<LogConnection> implements ILogSerialisation {

    public LogRepository( ){
        super();

    }

    @Override
    public Iterable<LogConnection> GetAllLogsForService(String service) {
        return null;
    }
}
