package Client.HoneyPot.lowinteraction;



import Client.HoneyPot.Repository.LogDatabase;
import Client.HoneyPot.Repository.LogRepository;
import Client.HoneyPot.logging.LogConnection;
import Client.Main.Main;

import java.util.Set;
import java.util.concurrent.Callable;

public class LIDeserializeDBThread implements Callable<Set<LogConnection>> {
    @Override
    public Set<LogConnection> call() throws Exception {
        return Main.GetLogs();
    }
}
