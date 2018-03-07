package Controller;

import HoneyPot.logging.LogConnection;
import HoneyPot.logging.LogRecord;
import HoneyPot.lowinteraction.LIModule;
import Main.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXToolbar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;

import Model.*;
import javafx.stage.Stage;
import sun.awt.SunToolkit;

public class OverviewController implements Initializable {
    @FXML
    AnchorPane anchor;
    @FXML
    JFXToolbar toolbar;
    @FXML
    JFXButton overviewBtn;
    @FXML
    JFXButton servicesBtn;
    @FXML
    JFXButton loginBtn;
    @FXML
    AnchorPane menuPane;
    @FXML
    Label statusLbl;
    @FXML
    Label threatLbl;
    @FXML
    Label timeframeLbl;
    @FXML
    Label connectionsLbl;
    Stage stage;
    static JFXSnackbar snackbar;
    int currentConnections = 0;

    @FXML
    public void changePage(ActionEvent event) {
        try {
            JFXButton source = (JFXButton) event.getSource();
            if (source == overviewBtn) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/OverView.fxml"), ResourceBundle.getBundle("bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
                Parent root = loader.load();
                OverviewController overview = loader.getController();
                overview.setStageAndSetupListeners(this.stage);
                Main.switchPage(root, "Achmea");
            } else if (source == loginBtn) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/LoginView.fxml"), ResourceBundle.getBundle("bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
                Parent root = loader.load();
                LoginController overview = loader.getController();
                overview.setStageAndSetupListeners(this.stage);
                Main.switchPage(root, "Achmea");
            } else if (source == servicesBtn) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ServicesView.fxml"), ResourceBundle.getBundle("bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
                Parent root = loader.load();
                ServicesController overview = loader.getController();
                overview.setStageAndSetupListeners(this.stage);
                Main.switchPage(root, "Achmea");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStageAndSetupListeners(Stage stage) {
        this.stage = stage;
        WindowButtons wb = new WindowButtons();
        WindowButtons wb2 = new WindowButtons(stage);
        toolbar.setRightItems(wb2, wb);
        Button en = addLanguageBtns("en.png");
        Button nl = addLanguageBtns("nl.png");
        toolbar.setLeftItems(en, nl);
        Timer timer = new Timer();
        timer.schedule(new OverViewTimer(this), 0, 5000);
    }

    private void loadView(String lang) {
        try {
            Main.lang = lang;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/OverView.fxml"), ResourceBundle.getBundle("bundles.UIResources", new Locale(lang, lang.toUpperCase())));
            Parent root = loader.load();
            OverviewController overviewCon = loader.getController();
            overviewCon.setStageAndSetupListeners(this.stage);
            Main.switchPage(root, "Achmea");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Button addLanguageBtns(String lang) {
        Button btn = new Button("");
        btn.setMaxSize(25, 25);
        btn.setMinSize(25, 25);
        btn.setPrefSize(25, 25);
        Image img = new Image("/Resources/" + lang);
        ImageView toggleImage = new ImageView(img);
        toggleImage.setFitWidth(20);
        toggleImage.setFitHeight(15);
        btn.setGraphic(toggleImage);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: achmeaPink; -fx-alignment: bottom-center");
        btn.setOnAction(actionEvent -> {
            if (lang.equals("en.png")) {
                loadView("en");
            } else {
                loadView("nl");
            }
        });
        return btn;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        overviewBtn.setDefaultButton(true);
        snackbar = new JFXSnackbar(anchor);
    }





    public int GetTotalConnections() {

        int i = 0;
        for (LIModule item : Main.Services) {

            i += item.getNumberOfActiveConnections();

        }
        if (currentConnections < i)
        {
            snackbar.show("New connection has been made!", "Okay", event -> snackbar.close());
        }
        currentConnections = i;
        return i;
    }

    public String getDatelastlog() {
        if (Main.honeypot.getLogs() != null) {
            SimpleDateFormat ft =
                    new SimpleDateFormat("dd.MM.yy 'at' hh:mm:ss");
            Date date = null;
            for (LogConnection item : Main.honeypot.getLogs()) {
                if (date == null) {
                    date = item.getDate();
                }
                if (item.getDate().after(date)) {
                    date = item.getDate();
                }

            }
            return ft.format(date);
        }
        return "No logs";
    }


    public static Status StatusCheck() {
        int start = 0;
        int connections = 0;

        for (LIModule item : Main.Services) {
            if (item.isStarted()) {
                start++;
            }
            connections += item.getNumberOfActiveConnections();
        }

        if (connections >= Main.ConnectionAlert) {
            return Status.ALERT;
        }

        if (start == 0) {
            return Status.OFF;
        }
        return Status.OK;
    }


    public int Timeframes() {
        int i = 0;
        if (Main.honeypot.getLogs() != null) {

            for (LogConnection item : Main.honeypot.getLogs()) {
                if (item.getDate().getTime() >= new Date(System.currentTimeMillis() - 3600 * 1000).getTime()) {
                    i++;
                }
            }

        }

        return i;
    }
}
