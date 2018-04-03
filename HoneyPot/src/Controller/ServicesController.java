package Controller;

import HoneyPot.logging.LogConnection;
import HoneyPot.lowinteraction.LIModule;
import Main.Main;
import Model.Status;
import Model.WindowButtons;
import com.jfoenix.controls.*;
import com.sun.javafx.scene.control.skin.TableColumnHeader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    @FXML
    TableView table;
    @FXML
    Label protocolLbl;
    @FXML
    HBox hb;
    @FXML
    HBox hb2;
    @FXML
    VBox vb2;
    @FXML
    VBox vb1;
    @FXML
    JFXToggleButton protoToggle;
    Stage stage;
    static JFXSnackbar snackbar;
    LIModule currentMod = null;

    JFXDialogLayout content = new JFXDialogLayout();
    @FXML
    StackPane stackPane;
    JFXDialog dialog;
    JFXButton button;
    private ResourceBundle resource;

    TableColumn ipColumn;
    TableColumn timeColumn;
    TableColumn portColumn;
    TableColumn messagesColumn;

    ScrollPane scrollPane;

    protected LIModule selectedMod;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hb2.setVisible(false);
        scrollPane = new ScrollPane();
        this.resource = resources;
        Timer timer = new Timer();
        timer.schedule(new ServicesTimer(this), 0, 2000);
        Main.manager.setToolbar(this.toolbar);
        snackbar = new JFXSnackbar(anchor);
        if (Main.account != null) {
            loginBtn.setText(ResourceBundle.getBundle("bundles.UIResources", new Locale(Main.lang.toUpperCase())).getString("logout"));
        }

        createTable();

        dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOverlayClose(false);
        content.setPrefWidth(600);
        content.prefHeightProperty().bind(hb2.heightProperty().multiply(0.9));
        button = new JFXButton("Close");
        hb.prefHeightProperty().bind(vb2.heightProperty().divide(1.75));
        vb1.setEffect(new DropShadow(3, Color.rgb(0, 0, 0, 0.8)));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Main.Stage.show();
                        dialog.close();
                        hb2.setVisible(false);
                    }
                });

            }
        });
        content.setHeading(new Text("Messages"));
        content.setActions(button);
    }

    public void createTable() {
        table.setEditable(true);

        ipColumn = new TableColumn(resource.getString("ip"));
        ipColumn.prefWidthProperty().bind(hb.widthProperty().divide(4));
        ipColumn.setCellValueFactory(
                new PropertyValueFactory<TableObject, String>("ip"));

        timeColumn = new TableColumn(resource.getString("time"));
        timeColumn.prefWidthProperty().bind(hb.widthProperty().divide(4));
        timeColumn.setCellValueFactory(
                new PropertyValueFactory<TableObject, String>("time"));

        portColumn = new TableColumn(resource.getString("port"));
        portColumn.prefWidthProperty().bind(hb.widthProperty().divide(4));
        portColumn.setCellValueFactory(
                new PropertyValueFactory<TableObject, String>("port"));

        messagesColumn = new TableColumn(resource.getString("message"));
        messagesColumn.prefWidthProperty().bind(hb.widthProperty().divide(4));
        messagesColumn.setCellValueFactory(
                new PropertyValueFactory<TableObject, String>("message"));
        table.getColumns().addAll(ipColumn, timeColumn, portColumn, messagesColumn);
    }

    public void fillListView() {
        serviceList.getItems().clear();
        connectionList.getItems().clear();
        iOList.getItems().clear();
        Label l1 = new Label(resource.getString("protocol"));
        Label l2 = new Label(resource.getString("connection"));
        Label l3 = new Label(resource.getString("stat"));
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

    public void clickLog(MouseEvent event) {
        if(event.getClickCount() == 2 && !hb2.isVisible()){
            try {
                if (!(event.getTarget() instanceof TableColumnHeader)) {
                    if (table.getSelectionModel().getSelectedItem() != null) {
                        TableObject selectedObject = (TableObject) table.getSelectionModel().getSelectedItem();
                        LogMessage(selectedObject);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void LogMessage(TableObject selectedObject){

        LogConnection log = selectedObject.getMessage();
        if (log.getLogRecords().size() > 0) {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    scrollPane.setContent(new Text(log.message()));
                    content.setBody(scrollPane);
                    dialog.show();
                    hb2.setVisible(true);
                    System.out.println("Opening");
                    System.out.println("Open: " + hb2.isVisible());
                }
            });
        }
    }




    @FXML
    public void clickProtocol(MouseEvent event) {
        try {
            JFXListView source = (JFXListView) event.getSource();
            if (source.getSelectionModel().getSelectedItem().getClass().equals(LIModule.class)) {
                LIModule mod = (LIModule) source.getSelectionModel().getSelectedItem();
                selectedMod = mod;
                LogGridUpdate(mod);
            }

        } catch (Exception e) {

        }
    }

    protected void LogGridUpdate(LIModule mod){
        Platform.runLater(() -> {
            table.getItems().clear();
            for (LogConnection log :
                    GetLogs(mod)) {
                SimpleDateFormat ft =
                        new SimpleDateFormat("dd.MM.yy 'at' hh:mm:ss");

                TableObject tableO = new TableObject(
                        log.getDstIP().getHostAddress().toString(),
                        log,
                        String.valueOf(log.getDstPort()),
                        ft.format(log.getDate()));
                table.getItems().add(tableO);
            }
            if (mod.isStarted()) {
                protoToggle.setSelected(true);
            } else {
                protoToggle.setSelected(false);
            }
            protocolLbl.setText(mod.toString());
            currentMod = mod;
        });

    }

    @FXML
    public void toggleMod() {
        if (currentMod != null)
            IO(currentMod);
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
                if (!Main.CheckForLogout(loginBtn.getText(), snackbar)) {
                    return;
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
     *
     * @param module Service module
     */
    public void TurnOff(LIModule module) {
        Main.honeypot.DeRegisterService(module);
    }

    /**
     * Start up module service
     *
     * @param module Service module
     */
    public void StartUp(LIModule module) {
        Main.honeypot.RegisterService(module);
        Main.honeypot.startPort(module.getPort());
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
     *
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
     *
     * @param module Service module
     * @return list of Logconnection
     */
    public LinkedList<LogConnection> GetLogs(LIModule module) {

        LinkedList<LogConnection> logs = Main.honeypot.getLogs();
        LinkedList<LogConnection> Servicelogs = new LinkedList<>();
        if (logs != null) {
            for (LogConnection item : logs) {
                if (item.getProtocol().equals(module.getProtocol().toString())) {
                    Servicelogs.add(item);
                }
            }
        }
        return Servicelogs;
    }


    /**
     * Get all the modules from the main
     *
     * @return list of modules
     */
    public ArrayList<LIModule> GetModules() {

        return Main.Services;
    }

    /**
     * Timeframe of 1 hour of return the amount of logs where create in the last hour
     *
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
     *
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
     *
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

    public class TableObject {
        public String ip;
        public LogConnection message;
        public String port;
        public String time;

        public TableObject(String ip, LogConnection message, String port, String time) {
            this.ip = ip;
            this.message = message;
            this.port = port;
            this.time = time;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public LogConnection getMessage() {
            return message;
        }

        public void setMessage(LogConnection message) {
            this.message = message;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
