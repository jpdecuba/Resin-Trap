package Controller;

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

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class AdminController extends BaseController implements Initializable  {

    private ResourceBundle resource;

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
    JFXButton adminBtn;
    @FXML
    VBox vb;

    static JFXSnackbar snackbar;
    final static String smpt = "SMTP";
    final static String ftp = "FTP";
    final static String irc = "IRC";
    final static String mysql = "MySQL";
    final static String dns = "DNS";
    final static String blank = "Blank";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;

        BarChart bc = CreateBarChart();
        Main.manager.setToolbar(this.toolbar);
        snackbar = new JFXSnackbar(anchor);
        LineChart lc = CreateLineChart();
        vb.getChildren().addAll(lc, bc);

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
        series1.setName("March");
        series1.getData().add(new XYChart.Data(smpt, 25601.34));
        series1.getData().add(new XYChart.Data(ftp, 20148.82));
        series1.getData().add(new XYChart.Data(irc, 10000));
        series1.getData().add(new XYChart.Data(mysql, 35407.15));
        series1.getData().add(new XYChart.Data(dns, 12000));

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
        series1.setName("March");
        series1.getData().add(new XYChart.Data("Day 1", 25601.34));
        series1.getData().add(new XYChart.Data("Day 2", 20148.82));
        series1.getData().add(new XYChart.Data("Day 3", 10000));
        series1.getData().add(new XYChart.Data("Day 4", 35407.15));
        series1.getData().add(new XYChart.Data("Day 5", 12000));
        series1.getData().add(new XYChart.Data("Day 6", 25601.34));
        series1.getData().add(new XYChart.Data("Day 7", 20148.82));
        series1.getData().add(new XYChart.Data("Day 8", 10000));
        series1.getData().add(new XYChart.Data("Day 9", 35407.15));
        series1.getData().add(new XYChart.Data("Day 10", 12000));

        lc.getData().addAll(series1);
        return lc;
    }


}
