package Main;

import Controller.OverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    Stage Stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/OverView.fxml"));
        Parent root = loader.load();
        OverviewController overview = loader.getController();
        this.Stage = primaryStage;
        Scene scene = new Scene(root);
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto");
        scene.getStylesheets().add("Resources/style.css");
        Stage.setTitle("Achmea");
        Stage.setScene(scene);
        Stage.setMinWidth(900);
        Stage.setMinHeight(600);
        Stage.show();
    }
}
