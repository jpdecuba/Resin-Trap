package HoneyPot.lowinteraction;

import HoneyPot.Repository.LogDatabase;
import HoneyPot.Repository.LogRepository;
import HoneyPot.logging.LogConnection;
import Main.Main;

public class LISerializeDBThread implements Runnable {
    private LogConnection log;
    private LogRepository repository;

    public LISerializeDBThread(LogConnection log){
        this.log = log;
        repository = new LogRepository(new LogDatabase());
    }

    @Override
    public void run() {
        repository.SaveLog(log, Main.GetAccount());
    }
}
