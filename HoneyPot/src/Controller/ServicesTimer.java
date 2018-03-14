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


    /**
     * ServicesTimer Constructor
     * @param item
     */
    public ServicesTimer(ServicesController item) {
        this.item = item;
    }

    @Override
    public void run() {
        Platform.runLater(()->{
            item.fillListView();
        });



    }
}
