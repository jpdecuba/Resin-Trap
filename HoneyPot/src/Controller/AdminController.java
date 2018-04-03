package Controller;

import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable  {

    final static String smpt = "SMPT";
    final static String ftp = "FTP";
    final static String irc = "IRC";
    final static String mysql = "MySQL";
    final static String dns = "DNS";
    final static String blank = "Blank";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BarChart bc = CreateBarChart();
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
}
