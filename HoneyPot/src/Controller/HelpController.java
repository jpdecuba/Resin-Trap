package Controller;

import Main.Main;
import com.jfoenix.controls.JFXToolbar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpController extends BaseController implements Initializable {
    @FXML
    JFXToolbar toolbar;

    @FXML
    AnchorPane anchor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.manager.setToolbar(this.toolbar);
    }
}
