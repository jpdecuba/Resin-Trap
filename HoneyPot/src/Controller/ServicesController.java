package Controller;

import HoneyPot.logging.LogConnection;
import HoneyPot.lowinteraction.LIModule;
import Main.Main;
import Model.Status;
import Model.WindowButtons;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.awt.image.ImageWatched;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;



public class ServicesController implements Initializable {
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
    JFXListView serviceList;
    @FXML
    JFXListView connectionList;
    @FXML
    JFXListView iOList;
    Stage stage;
    static JFXSnackbar snackbar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
		Timer timer = new Timer();
		timer.schedule(new ServicesTimer(this), 0,5000);
        Main.manager.setToolbar(this.toolbar);
        snackbar = new JFXSnackbar(anchor);
        if (Main.account != null) {
            loginBtn.setText(ResourceBundle.getBundle("bundles.UIResources", new Locale(Main.lang.toUpperCase())).getString("logout"));
        }

    }

    public void fillListView() {
        serviceList.getItems().clear();
        connectionList.getItems().clear();
        iOList.getItems().clear();
        Label l1 = new Label("Protocol");
        Label l2 = new Label("Connections");
        Label l3 = new Label("Status");
        l1.setStyle("-fx-font-weight: BOLD");
        l2.setStyle("-fx-font-weight: BOLD");
        l3.setStyle("-fx-font-weight: BOLD");
        serviceList.getItems().add(l1);
        connectionList.getItems().add(l2);
        iOList.getItems().add(l3);
        connectionList.setEditable(false);
        for (LIModule mod :
                GetModules()) {
            serviceList.getItems().add(mod);
            connectionList.getItems().add(mod.getNumberOfActiveConnections());
            iOList.getItems().add(StatusCheck(mod).toString());
        }
    }

    @FXML
    public void changePage(ActionEvent event) {
        try {
            JFXButton source = (JFXButton) event.getSource();
            String path = "";
            String title = "";
            if (source == overviewBtn) {
                path = "/View/OverView.fxml";
                title = "Achmea";
            } else if (source == loginBtn) {
                if (loginBtn.getText() == "Logout") {
                    if (Main.loginModel.Logout(Main.account.getName())) {
                        Main.account = null;
                    } else {
                        snackbar.show("Failed to logout", 3000);
                        return;
                    }
                }
                path = "/View/LoginView.fxml";
                title = "Achmea";
            } else if (source == servicesBtn) {
                path = "/View/ServicesView.fxml";
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

    public void setStageAndSetupListeners(Stage stage) {
        this.stage = stage;
        WindowButtons wb = new WindowButtons();
        WindowButtons wb2 = new WindowButtons(stage);
        toolbar.setRightItems(wb2, wb);
        Button en = addLanguageBtns("en.png");
        Button nl = addLanguageBtns("nl.png");
        toolbar.setLeftItems(en, nl);
    }

    private void loadView(String lang) {
        try {
            Main.lang = lang;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/OverView.fxml"), ResourceBundle.getBundle("bundles.UIResources", new Locale(lang, lang.toUpperCase())));
            Parent root = loader.load();
            OverviewController overviewCon = loader.getController();
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

    public int GetServiceson() {

        int i = 0;
        for (LIModule item : Main.Services) {

            if (item.isStarted()) {
                i++;
            }
        }
        return i;
    }

    /**
     * Turns Off the module service
     * @param module Service module
     */
    public void TurnOff(LIModule module) {
        Main.honeypot.DeRegisterService(module);
    }

    /**
     * Start up module service
     * @param module Service module
     */
    public void StartUp(LIModule module) {
        Main.honeypot.DeRegisterService(module);
    }

    public void IO(LIModule module) {

        if (module.isStarted()) {
            TurnOff(module);
        } else {
            StartUp(module);
        }

    }


    /**
     * Checking if module is running
     * @param module Service module
     * @return true or false if it's started
     */
    public boolean isStarted(LIModule module) {
        return module.isStarted();
    }

    public int GetConnections(LIModule module) {
        return module.getNumberOfActiveConnections();
    }

    /**
     * Get log from the Service module
     * @param module Service module
     * @return list of Logconnection
     */
    public LinkedList<LogConnection> GetLogs(LIModule module){

        LinkedList<LogConnection> logs = Main.honeypot.getLogs();
        LinkedList<LogConnection> Servicelogs = new LinkedList<>();
        for (LogConnection item : logs) {
            if (item.getProtocol().equals(module.getProtocol().toString())) {
                Servicelogs.add(item);
            }
        }
        return logs;
    }


    /**
     * Get all the modules from the main
     * @return list of modules
     */
    public ArrayList<LIModule> GetModules() {

        return Main.Services;
    }

    /**
     * Timeframe of 1 hour of return the amount of logs where create in the last hour
     * @param logs LinkedList of logConnection items
     * @return about of logs made in 1 hour
     */
    public int Timeframes(LinkedList<LogConnection> logs) {
        int i = 0;
        if (logs != null) {

            for (LogConnection item : logs) {
                if (item.getDate().getTime() >= new Date(System.currentTimeMillis() - 3600 * 1000).getTime()) {
                    i++;
                }
            }

        }

        return i;
    }

    /**
     * Get data of the last log file (time)
     * @param logs LinkedList of logConnection items
     * @return string of the last log data
     */

    public String getDatelastlog(LinkedList<LogConnection> logs) {
        if (logs != null) {
            SimpleDateFormat ft =
                    new SimpleDateFormat("dd.MM.yy 'at' hh:mm:ss");
            Date date = null;
            for (LogConnection item : logs) {
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

    /**
     * StatusCheck
     * @param module Service module
     * @return returns a status item base of module input
     */
    public Status StatusCheck(LIModule module) {
        int connections = module.getNumberOfActiveConnections();
        if (connections >= Main.ConnectionAlert) {
            return Status.ALERT;
        }

        if (module.isStarted()) {
            return Status.OK;
        }
        return Status.OFF;
    }


}
