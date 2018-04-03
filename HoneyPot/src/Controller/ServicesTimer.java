package Controller;

import javafx.application.Platform;

import java.util.TimerTask;

public class ServicesTimer extends TimerTask {


    private ServicesController item;


    /**
     * ServicesTimer Constructor
     * @param item ServidcesDController
     */
    public ServicesTimer(ServicesController item) {
        this.item = item;
    }

    @Override
    public void run() {
        Platform.runLater(()->{
            item.fillListView();
            if(item.selectedMod != null) {
                item.LogGridUpdate(item.selectedMod);
            }

        });



    }
}
