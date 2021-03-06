package Client.Controller;

import Client.HoneyPot.lowinteraction.ILIModule;
import Client.Main.Main;
import Client.Controller.ControllerTimer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXToolbar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;

public class BaseController {

    @FXML JFXButton overviewBtn;
    @FXML JFXButton servicesBtn;
    @FXML JFXButton loginBtn;
    @FXML JFXButton adminBtn;
    @FXML JFXButton settingBtn;
    @FXML AnchorPane loadPane;

    static JFXSnackbar snackbar;
    Timer timer;

	public BaseController()
	{
		timer = new Timer();
		timer.schedule(new ControllerTimer(this), 0,5000);
	}

    public void LoadPaneOn() {
        Platform.runLater(() -> {
            loadPane.setVisible(true);
        });
    }

    public void LoadPaneOff() {
        Platform.runLater(() -> {
            loadPane.setVisible(false);
        });
    }

    @FXML
    public void changePage(ActionEvent event) {

        try {
            JFXButton source = (JFXButton) event.getSource();
            String path = "";
            String title = "";

            if (source == overviewBtn){
                path = "/Client/View/OverView.fxml";
                title = "Achmea";
            } else if (source == loginBtn){
                if(!Main.CheckForLogout(loginBtn.getText(), snackbar))
                {
                    return;
                }
                path = "/Client/View/LoginView.fxml";
                title = "Login";
            }
            else if (source == servicesBtn){
                path = "/Client/View/ServicesView.fxml";
                title = "Achmea";
            }
            else if (source == adminBtn){
                path = "/Client/View/AdminView.fxml";
                title = "Admin";
            }
            else if (source == settingBtn)
			{
				path = "/Client/View/SettingView.fxml";
				title = "Setting";
			}
            Main.manager.currentView = path;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path), ResourceBundle.getBundle("Client.Bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
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
        for (ILIModule item : Main.Services) {

            i += item.getNumberOfActiveConnections();

        }
        if (Main.currentConnections < i)
        {
        	if(SystemTray.isSupported())
			{
				for (TrayIcon t: SystemTray.getSystemTray().getTrayIcons())
				{
					if(t.getToolTip() == "ResinTrap")
					{
						t.displayMessage("Threat!", "There is an active connection! Open the application to see more.", TrayIcon.MessageType.WARNING);
					}
				}

			}
            Platform.runLater(() -> {
                snackbar.show("New connection has been made!", "Help", 3000, event -> {
                    try {
                        snackbar.close();
                        String path = "/Client/View/OSView.fxml";
                        String title = "Achmea";
                        Main.manager.currentView = path;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(path), ResourceBundle.getBundle("Client.Bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
                        Parent root = loader.load();
                        Main.switchPage(root, title);
                        timer = new Timer();
                        timer.schedule(new ControllerTimer(this), 0, 5000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });
        }
        Main.currentConnections = i;
        return i;
    }
}
