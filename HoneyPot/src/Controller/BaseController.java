package Controller;

import HoneyPot.lowinteraction.ILIModule;
import Main.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXToolbar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;

public class BaseController {

    @FXML
    JFXButton overviewBtn;
    @FXML
    JFXButton servicesBtn;
    @FXML
    JFXButton loginBtn;
    @FXML
    JFXButton adminBtn;

    static JFXSnackbar snackbar;
    int currentConnections = 0;
	Timer timer;

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
            else if (source == adminBtn){
                path = "/View/AdminView.fxml";
                title = "Achmea";
            }
            Main.manager.currentView = path;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path), ResourceBundle.getBundle("bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
            Parent root = loader.load();
            Main.switchPage(root, title);
			Platform.runLater(() -> {
				timer = new Timer();
				timer.schedule(new ControllerTimer(this), 0,5000);
			});
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
        if (currentConnections < i)
        {
            snackbar.show("New connection has been made!", "Okay", event -> snackbar.close());
        }
        currentConnections = i;
        return i;
    }
}
