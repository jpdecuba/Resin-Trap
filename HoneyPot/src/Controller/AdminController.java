package Controller;

import HoneyPot.logging.LogConnection;
import HoneyPot.lowinteraction.LIModule;
import Main.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXToolbar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class AdminController extends BaseController implements Initializable  {

    private ResourceBundle resource;

    @FXML
    AnchorPane anchor;
    @FXML
    JFXToolbar toolbar;
    @FXML
    VBox vb;

    static JFXSnackbar snackbar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;

        BarChart bc = CreateBarChart();
        Main.manager.setToolbar(this.toolbar);
        snackbar = new JFXSnackbar(anchor);
        LineChart lc = CreateLineChart();
        vb.getChildren().addAll(lc, bc);
        Main.ChangeLoginButton(loginBtn);

    }

    public BarChart<String,Number> CreateBarChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<>(xAxis,yAxis);
        bc.setTitle("Threats by protocol");
        xAxis.setLabel("Protocol");
        yAxis.setLabel("Threats");
        bc.prefHeightProperty().bind(vb.heightProperty().divide(2));
        bc.prefWidthProperty().bind(vb.widthProperty());
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("April");
        for (LIModule mod :
                Main.Services) {
            series1.getData().add(new XYChart.Data(mod.getProtocol().toString(), GetLogs(mod)));
        }
        bc.getData().addAll(series1);
        return bc;
    }

    public LineChart<String,Number> CreateLineChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<String,Number> lc =
                new LineChart<>(xAxis,yAxis);
        lc.setTitle("Threats per day");
        xAxis.setLabel("Day");
        yAxis.setLabel("Threats");
        lc.prefHeightProperty().bind(vb.heightProperty().divide(2));
        lc.prefWidthProperty().bind(vb.widthProperty());
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("April");
        for (int i = 744; i >= 0; i = i - 24){
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, -i);
            DateFormat dateFormat = new SimpleDateFormat("dd MMM");
            String d = dateFormat.format(today.getTime());
            XYChart.Data data = new XYChart.Data(d, GetLogs2(today));
            series1.getData().add(data);
            Rectangle rect = new Rectangle(0, 0);
            rect.setVisible(false);
            data.setNode(rect);
        }

        lc.getData().addAll(series1);
        return lc;
    }

    public int GetLogs(LIModule module) {

        LinkedList<LogConnection> logs = Main.honeypot.getLogs();
        int logsAmount = 0;
        if (logs != null) {
            for (LogConnection item : logs) {
                if (item.getProtocol().equals(module.getProtocol().toString())) {
                    logsAmount++;
                }
            }
        }
        return logsAmount;
    }

    public int GetLogs2(Calendar date) {

        LinkedList<LogConnection> logs = Main.honeypot.getLogs();
        int logsAmount = 0;
        if (logs != null) {
            for (LogConnection item : logs) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(item.getDate());
                boolean sameDay = cal.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                        cal.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR);
                if (sameDay) {
                    logsAmount++;
                }
            }
        }
        return logsAmount;
    }
}
