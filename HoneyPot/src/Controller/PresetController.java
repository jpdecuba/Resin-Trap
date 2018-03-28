package Controller;

import Main.Main;
import com.jfoenix.controls.JFXToolbar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class PresetController implements Initializable {
    @FXML
    AnchorPane anchor;
    @FXML
    JFXToolbar toolbar;
    @FXML
    VBox centerPane;
    @FXML
    VBox vb;
    @FXML
    RadioButton expressRBtn;
    @FXML
    RadioButton customRBtn;
    @FXML
    RadioButton homeRBtn;
    @FXML
    RadioButton small1RBtn;
    @FXML
    RadioButton small2RBtn;
    @FXML
    RadioButton big1RBtn;
    @FXML
    RadioButton big2RBtn;
    @FXML
    Label presetLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vb.setVisible(false);
        presetLbl.setVisible(false);
        Main.manager.setToolbar(this.toolbar);
        centerPane.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.8)));
    }

    @FXML
    public void toggle1(ActionEvent action) {
        RadioButton rb = (RadioButton) action.getSource();
        if (rb == expressRBtn) {
            vb.setVisible(false);
            presetLbl.setVisible(false);
        } else {
            vb.setVisible(true);
            presetLbl.setVisible(true);
        }
    }

    @FXML
    public void toggle2(ActionEvent action) {
        RadioButton rb = (RadioButton) action.getSource();
        Platform.runLater(() ->{
            presetLbl.setText(ResourceBundle.getBundle("bundles.UIResources",new Locale(Main.lang.toUpperCase())).getString(rb.getText().replaceAll("\\s+","")));
        });
    }

    @FXML
    public void next(){
        if (expressRBtn.isSelected()){
            //express func
        } else {
            //check chosen radio button
        }
    }
}
