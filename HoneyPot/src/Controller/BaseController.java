package Controller;

import Main.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXToolbar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

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

    @FXML
    public void changePage(ActionEvent event) {

        try {
            JFXButton source = (JFXButton) event.getSource();
            String path = "";
            String title = "";

            if (source == overviewBtn){
                path = "/View/HelpView.fxml";
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
