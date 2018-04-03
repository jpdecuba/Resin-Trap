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
    AnchorPane mainPane;
    static JFXSnackbar snackbar;
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
        Main.manager.setToolbar(this.toolbar);
        snackbar = new JFXSnackbar(anchor);
    }

    public BarChart<String,Number> CreateBarChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<>(xAxis,yAxis);
        bc.setTitle("Threats by protocol");
        xAxis.setLabel("Protocol");
        yAxis.setLabel("Threats");
        bc.prefHeightProperty().bind(mainPane.heightProperty());
        bc.prefWidthProperty().bind(mainPane.widthProperty());
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
        try {
            JFXButton source = (JFXButton) event.getSource();
            String path = "";
            String title = "";

            if (source == overviewBtn){
                path = "/View/OverView.fxml";
                title = "Achmea";
            } else if (source == loginBtn){
                if(!Main.CheckForLogout(loginBtn.getText(), snackbar))
                {
                    return;
                }
                path = "/View/LoginView.fxml";
                title = "Login";
            }
            else if (source == servicesBtn){
                path = "/View/ServicesView.fxml";
                title = "Achmea";
            }
            else if (source == adminBtn){
                path = "/View/AdminView.fxml";
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
}
