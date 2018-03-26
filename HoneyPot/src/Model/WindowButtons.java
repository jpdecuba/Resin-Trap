package Model;

import Main.Main;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class WindowButtons extends HBox {

    boolean fullscreen;
    MaterialDesignIconView fa;
    MaterialDesignIconView fa2;

    public WindowButtons() {
        Button closeBtn = new Button("");
        this.setStyle("-fx-alignment: center");
        closeBtn.setMaxSize(25, 25);
        closeBtn.setMinSize(25, 25);
        closeBtn.setPrefSize(25, 25);
        MaterialDesignIconView fa = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_CLOSE);
        fa.setFill(Color.valueOf("#c15683"));
        fa.setSize("18");
        closeBtn.setGraphic(fa);
        closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: achmeaPink; -fx-alignment: bottom-center");
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                Main.Stage.close();
                Main.Shutdown();
                System.exit(0);
            }
        });
        this.getChildren().add(closeBtn);

    }

    public WindowButtons(Stage stage) {
        Button maxBtn = new Button("");
        this.setStyle("-fx-alignment: center");
        maxBtn.setMaxSize(25, 25);
        maxBtn.setMinSize(25, 25);
        maxBtn.setPrefSize(25, 25);
        fa = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_MAXIMIZE);
        fa2 = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_RESTORE);
        fa.setFill(Color.valueOf("#c15683"));
        fa2.setFill(Color.valueOf("#c15683"));
        maxBtn.setGraphic(fa);
        fa.setSize("18");
        fa2.setSize("18");

        fullscreen = false;

        maxBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: achmeaPink; -fx-alignment: baseline-center");
        maxBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                if (!fullscreen) {
                    fullscreen = true;
                    stage.setMaximized(true);
                    maxBtn.setGraphic(fa2);
                }
                else {
                    fullscreen = false;
                    stage.setMaximized(false);
                    maxBtn.setGraphic(fa);
                }
            }
        });

        this.getChildren().add(maxBtn);
    }
}
