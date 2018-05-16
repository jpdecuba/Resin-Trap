package Client.Model;

import Client.Controller.OverviewController;
import Client.Main.Main;
import Shared.Model.User;
import Shared.Model.UserRole;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToolbar;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerManager {

    public String currentView = "";

    public void loadView(String lang, String path, String title) {
        try {
            Main.lang = lang;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            loader.setResources(ResourceBundle.getBundle("Client.bundles.UIResources", new Locale(lang, lang.toUpperCase())));
            Parent root = loader.load();
            Main.switchPage(root, title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setToolbar(JFXToolbar toolbar) {
        WindowButtons wb = new WindowButtons();
        WindowButtons wb2 = new WindowButtons(Main.Stage);
        toolbar.setRightItems(wb2, wb);
        Button en = addLanguageBtns("en.png");
        Button nl = addLanguageBtns("nl.png");
        Button help = AddHelpBtn();
        toolbar.setLeftItems(en, nl, help);
    }

    public Button AddHelpBtn() {
        Button btn = new Button("");
        btn.setMaxSize(25, 25);
        btn.setMinSize(25, 25);
        btn.setPrefSize(25, 25);
        MaterialDesignIconView fa = new MaterialDesignIconView(MaterialDesignIcon.INFORMATION_OUTLINE);
        fa.setFill(Color.valueOf("WHITE"));
        fa.setSize("21");
        btn.setGraphic(fa);
        btn.setStyle("-fx-background-color: transparent; -fx-alignment: center");
        btn.setOnAction(actionEvent -> {
            currentView = "/Client/View/HelpView.fxml";
            loadView(Main.lang, currentView, "Achmea");
        });
        return btn;
    }

    public Button addLanguageBtns(String lang) {
        Button btn = new Button("");
        btn.setMaxSize(25, 25);
        btn.setMinSize(25, 25);
        btn.setPrefSize(25, 25);
        Image img = new Image("/Client/Resources/" + lang);
        ImageView toggleImage = new ImageView(img);
        toggleImage.setFitWidth(20);
        toggleImage.setFitHeight(15);
        btn.setGraphic(toggleImage);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: achmeaPink; -fx-alignment: center");
        btn.setOnAction(actionEvent -> {
            if (lang.equals("en.png")) {
                loadView("en", currentView, "Achmea");
            } else {
                loadView("nl", currentView, "Achmea");
            }
        });
        return btn;
    }

    public void ChangeNavButtons(JFXButton log, JFXButton admin, JFXButton settings) {
        boolean enable = false;
        User account = Main.GetAccount();
        if (account != null) {
            ChangeLoginButton(log);
            if (account.getRole() == UserRole.Admin) {
                enable = true;
            }
        }
        admin.setDisable(!enable);
        admin.setVisible(enable);
        settings.setDisable(!enable);
        settings.setVisible(enable);
    }

    public void ChangeLoginButton(JFXButton log) {
        log.setText(ResourceBundle.getBundle("Client.bundles.UIResources", new Locale(Main.lang.toUpperCase())).getString("logout"));
    }
}
