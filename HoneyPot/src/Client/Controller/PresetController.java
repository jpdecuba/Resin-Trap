package Client.Controller;

import Client.FileSave.SaveFiles;
import Client.Main.Main;
import Client.Model.Preset.Preset;
import Client.Model.Preset.Soort;
import Client.Model.Preset.Type;
import com.jfoenix.controls.JFXToolbar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
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

    Preset preset = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vb.setVisible(false);
        presetLbl.setVisible(false);
        Main.manager.setToolbar(this.toolbar);
        centerPane.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.8)));
        preset = new Preset();
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
            presetLbl.setText(ResourceBundle.getBundle("Client.bundles.UIResources",new Locale(Main.lang.toUpperCase())).getString(rb.getText().replaceAll("\\s+","")));
        });
    }

    @FXML
    public void next(){
        if (expressRBtn.isSelected()){
            //express func
            preset.SetTypeAndSoort(Type.None, Soort.Express);
        } else {
            //check chosen radio button
            if(homeRBtn.isSelected()){
                preset.SetTypeAndSoort(Type.None, Soort.Home);
            }
            else if(small1RBtn.isSelected()){
                preset.SetTypeAndSoort(Type.Type_1, Soort.Company_Small);
            }
            else if(small2RBtn.isSelected()){
                preset.SetTypeAndSoort(Type.Type_2, Soort.Company_Small);
            }
            else if(big1RBtn.isSelected()){
                preset.SetTypeAndSoort(Type.Type_1, Soort.Company_Big);
            }
            else if(big2RBtn.isSelected()){
                preset.SetTypeAndSoort(Type.Type_2, Soort.Company_Big);
            }
        }

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/View/OverView.fxml"), ResourceBundle.getBundle("Client.bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
            Parent root = loader.load();
            Main.switchPage(root, "Achmea");
        } catch(IOException IOE){
            IOE.printStackTrace();
        }
    }
}
