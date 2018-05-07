package Client.HoneyPot.lowinteraction;

import Client.Main.Main;
import Client.HoneyPot.Repository.*;

import Client.HoneyPot.logging.LogConnection;


public class LISerializeDBThread implements Runnable {
    private LogConnection log;
    private LogRepository repository;

    public LISerializeDBThread(LogConnection log){
        this.log = log;
        //repository = new LogRepository(new LogDatabase());
    }

    @Override
    public void run() {
        //repository.SaveLog(log, Main.GetAccount());
        Main.SaveLog(log);
    }
}
