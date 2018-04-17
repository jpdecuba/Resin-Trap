package HoneyPot.lowinteraction;

import HoneyPot.Repository.LogDatabase;
import HoneyPot.Repository.LogRepository;
import HoneyPot.logging.LogConnection;

import java.util.Set;
import java.util.concurrent.Callable;

public class LIDeserializeDBThread implements Callable<Set<LogConnection>> {
    @Override
    public Set<LogConnection> call() throws Exception {
        LogRepository repository = new LogRepository(new LogDatabase());
        return repository.GetAllLogs();
    }
}
