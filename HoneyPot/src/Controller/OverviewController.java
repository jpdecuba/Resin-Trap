package Controller;

import com.jfoenix.controls.JFXToolbar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

import Model.*;

import javax.swing.*;

public class OverviewController implements Initializable {
    @FXML
    AnchorPane anchor;
    @FXML
    JFXToolbar toolbar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WindowButtons wb = new WindowButtons();
        toolbar.setRightItems(wb);
    }
}
