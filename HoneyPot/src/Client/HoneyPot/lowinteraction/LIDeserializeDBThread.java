package Client.HoneyPot.lowinteraction;

import Shared.Logging.LogConnection;
import Client.Main.Main;

import java.util.Set;
import java.util.concurrent.Callable;

public class LIDeserializeDBThread implements Callable<Set<LogConnection>> {
    @Override
    public Set<LogConnection> call() throws Exception {
        Main.GetLogs();
        return Main.logsdb;
    }
}
