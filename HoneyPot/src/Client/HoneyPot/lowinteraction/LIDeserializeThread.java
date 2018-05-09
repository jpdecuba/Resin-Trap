package Client.HoneyPot.lowinteraction;

import Client.HoneyPot.logging.LogConnection;
import Client.Model.Repositories.Local.LogLocal;
import Client.Model.Repositories.Repository.LogRepository;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class LIDeserializeThread implements Callable<Set<LogConnection>> {

    @Override
    public Set<LogConnection> call() throws Exception {
        LogRepository repo = new LogRepository(new LogLocal());
        return repo.GetAllLogs();
    }
}
