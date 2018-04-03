package Controller;

import Main.Main;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminController implements Initializable  {

    @FXML
    AnchorPane mainPane;
    final static String smpt = "SMTP";
    final static String ftp = "FTP";
    final static String irc = "IRC";
    final static String mysql = "MySQL";
    final static String dns = "DNS";
    final static String blank = "Blank";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BarChart bc = CreateBarChart();
        mainPane.getChildren().add(bc);
    }

    public BarChart<String,Number> CreateBarChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<>(xAxis,yAxis);
        bc.setTitle("Threats by protocol");
        xAxis.setLabel("Protocol");
        yAxis.setLabel("Threats");

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

    @FXML
    public void changePage(ActionEvent event) {

    }
}
