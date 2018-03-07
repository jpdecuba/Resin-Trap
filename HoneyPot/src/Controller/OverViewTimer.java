package Controller;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.TimerTask;

public class OverViewTimer extends TimerTask {


    private OverviewController item;



    public OverViewTimer(OverviewController item) {
        this.item = item;
    }

    @Override
    public void run() {

        Platform.runLater(()->{

            item.statusLbl.setText(item.StatusCheck().toString());
            item.connectionsLbl.setText(String.valueOf(item.GetTotalConnections()));
            item.threatLbl.setText(item.getDatelastlog());
            item.servicesLbl.setText(String.valueOf(item.GetServiceson()));
            item.Timeframes();
        });



    }
}
