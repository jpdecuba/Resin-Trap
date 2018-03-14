package Controller;

import HoneyPot.logging.LogConnection;
import HoneyPot.lowinteraction.LIModule;
import Main.Main;
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

import Model.*;

public class OverviewController implements Initializable {
    @FXML
    AnchorPane anchor;
    @FXML
    JFXToolbar toolbar;
    @FXML
    JFXButton overviewBtn;
    @FXML
    JFXButton servicesBtn;
    @FXML
    JFXButton loginBtn;
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
    static JFXSnackbar snackbar;
    int currentConnections = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		overviewBtn.setDefaultButton(true);
		Timer timer = new Timer();
		timer.schedule(new OverViewTimer(this), 0,5000);
		Main.manager.setToolbar(this.toolbar);
		snackbar = new JFXSnackbar(anchor);
		if(Main.account != null)
		{
			loginBtn.setText(ResourceBundle.getBundle("bundles.UIResources",new Locale(Main.lang.toUpperCase())).getString("logout"));
		}
	}

    @FXML
    public void changePage(ActionEvent event) {
        try {
            JFXButton source = (JFXButton) event.getSource();
			String path = "";
			String title = "";

            if (source == overviewBtn){
				path = "/View/OverView.fxml";
				title = "Achmea";
            } else if (source == loginBtn){
				if(!Main.CheckForLogout(loginBtn.getText(), snackbar))
				{
					return;
				}
            	path = "/View/LoginView.fxml";
            	title = "Login";
            }
            else if (source == servicesBtn){
            	path = "/View/ServicesView.fxml";
            	title = "Achmea";
            }
			Main.manager.currentView = path;
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path), ResourceBundle.getBundle("bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
			Parent root = loader.load();
			Main.switchPage(root, title);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Total Connection of all services
     * @return int of the totaal amount of connections
     */
    public int GetTotalConnections() {

        int i = 0;
        for (LIModule item : Main.Services) {

            i += item.getNumberOfActiveConnections();

        }
        if (currentConnections < i)
        {
            snackbar.show("New connection has been made!", "Okay", event -> snackbar.close());
        }
        currentConnections = i;
        return i;
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

        for (LIModule item : Main.Services) {
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
