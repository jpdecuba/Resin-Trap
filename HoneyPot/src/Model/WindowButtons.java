package Model;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class WindowButtons extends HBox {

    public WindowButtons() {
        Button closeBtn = new Button("x");
        closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: achmeaPink; -fx-font-family: Roboto; -fx-font-size: 22; -fx-font-weight: bold");
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });

        this.getChildren().add(closeBtn);
    }
}
