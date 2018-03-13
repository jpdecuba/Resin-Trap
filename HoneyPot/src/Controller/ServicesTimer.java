package Controller;

import HoneyPot.logging.LogConnection;
import HoneyPot.lowinteraction.LIModule;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TimerTask;

public class ServicesTimer extends TimerTask {


    private ServicesController item;



    public ServicesTimer(ServicesController item) {
        this.item = item;
    }

    @Override
    public void run() {


        ArrayList<LIModule> GetModules = item.GetModules();

        Platform.runLater(()->{
            for(LIModule module : GetModules) {
                item.isStarted(module);
                LinkedList<LogConnection> logs = item.GetLogs(module);
                item.GetConnections(module);
                item.Timeframes(logs);
                item.getDatelastlog(logs);
            }
        });



    }
}
