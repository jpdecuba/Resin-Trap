package Controller;

import Main.Main;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXToolbar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class PresetController implements Initializable{
    @FXML
    AnchorPane anchor;
    @FXML
    JFXToolbar toolbar;
    @FXML
    VBox centerPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.manager.setToolbar(this.toolbar);
        centerPane.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.8)));
    }

}
