package Client.Controller;

import Client.Main.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToolbar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class OSController extends BaseController implements Initializable {

    @FXML JFXToolbar toolbar;
    @FXML JFXButton windows;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.manager.setToolbar(this.toolbar);
        Main.manager.ChangeNavButtons(loginBtn, adminBtn, settingBtn);
    }

    public void chooseOS(ActionEvent event){
        try {
            JFXButton source = (JFXButton) event.getSource();
            String path = "/Client/View/HelpView.fxml";
            String title = "Achmea";
            Main.manager.currentView = path;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path), ResourceBundle.getBundle("Client.Bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
            Parent root = loader.load();
            HelpController helpController = loader.getController();
            helpController.setup(source == windows);
            Main.switchPage(root, title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
