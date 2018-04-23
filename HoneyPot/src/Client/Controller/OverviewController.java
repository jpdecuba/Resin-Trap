package Client.Controller;

import Client.HoneyPot.logging.LogConnection;
import Client.HoneyPot.lowinteraction.*;
import Client.Main.Main;
import Client.Model.Status;
import Client.HoneyPot.logging.LogConnection;

import Client.Main.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXToolbar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;

import Client.Model.*;

public class OverviewController extends BaseController implements Initializable {
    @FXML
    AnchorPane anchor;
    @FXML
    JFXToolbar toolbar;
    @FXML
    AnchorPane menuPane;
    @FXML
    Label statusLbl;
    @FXML
    Label threatLbl;
    @FXML
    Label timeframeLbl;
    @FXML
    Label connectionsLbl;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		overviewBtn.setDefaultButton(true);
		Main.manager.setToolbar(this.toolbar);
		Main.manager.currentView = "/Client/View/OverView.fxml";
		snackbar = new JFXSnackbar(anchor);
		Main.ChangeLoginButton(loginBtn);
		Main.ChangeAdminButton(adminBtn);

	}

    /**
     * Get data of the last log file (time)
     * @return string of the last time of the last record that was made
     */
    public String getDatelastlog() {
        if (Main.honeypot.getLogs() != null) {
            SimpleDateFormat ft =
                    new SimpleDateFormat("dd.MM.yy 'at' hh:mm:ss");
            Date date = null;
            for (LogConnection item : Main.honeypot.getLogs()) {
                if (date == null) {
                    date = item.getDate();
                }
                if (item.getDate().after(date)) {
                    date = item.getDate();
                }

            }
            return ft.format(date);
        }
        return "No logs";
    }


    /**
     * Statuscheck for all the services
     * @return status of all the services that was made
     */
    public static Status StatusCheck() {
        int start = 0;
        int connections = 0;

        for (ILIModule item : Main.Services) {
            if (item.isStarted()) {
                start++;
            }
            connections += item.getNumberOfActiveConnections();
        }

        if (connections >= Main.ConnectionAlert) {
            return Status.ALERT;
        }

        if (start == 0) {
            return Status.OFF;
        }
        return Status.OK;
    }

    /**
     * Timeframe of 1 hour of return the amount of logs where create in the last hour
     * @return int of the amount of logs that where made in the last hour
     */

    public int Timeframes() {
        int i = 0;
        if (Main.honeypot.getLogs() != null) {

            for (LogConnection item : Main.honeypot.getLogs()) {
                if (item.getDate().getTime() >= new Date(System.currentTimeMillis() - 3600 * 1000).getTime()) {
                    i++;
                }
            }
        }

        return i;
    }
}
