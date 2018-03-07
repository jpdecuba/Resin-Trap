package Model;

import Main.Main;
import com.jfoenix.controls.JFXButton;
import com.sun.imageio.plugins.common.I18N;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Locale;

public class WindowButtons extends HBox {

    public WindowButtons() {
        Button closeBtn = new Button("");
        this.setStyle("-fx-alignment: center");
        closeBtn.setMaxSize(25, 25);
        closeBtn.setMinSize(25, 25);
        closeBtn.setPrefSize(25, 25);
        FontAwesomeIconView fa = new FontAwesomeIconView();
        fa.setGlyphName("CLOSE");
        fa.setFill(Color.valueOf("#c15683"));
        fa.setSize("22");
        closeBtn.setGraphic(fa);
        closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: achmeaPink; -fx-alignment: bottom-center");
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                Main.Shutdown();
                Platform.exit();
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
        FontAwesomeIconView fa = new FontAwesomeIconView();
        fa.setGlyphName("PLUS");
        fa.setFill(Color.valueOf("#c15683"));
        maxBtn.setGraphic(fa);
        fa.setSize("22");
        maxBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: achmeaPink; -fx-alignment: baseline-center");
        maxBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();
                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
                stage.setWidth(bounds.getWidth());
                stage.setHeight(bounds.getHeight());
            }
        });

        this.getChildren().add(maxBtn);
    }
}
