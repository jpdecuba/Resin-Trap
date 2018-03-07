package Model;

import Controller.OverviewController;
import Main.Main;
import com.jfoenix.controls.JFXToolbar;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerManager {

	public void loadView(String lang, String path, String title) {
		try {
			Main.lang = lang;
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path), ResourceBundle.getBundle("bundles.UIResources", new Locale(lang, lang.toUpperCase())));
			Parent root = loader.load();
			Main.switchPage(root, title);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setToolbar(JFXToolbar toolbar) {
		WindowButtons wb = new WindowButtons();
		WindowButtons wb2 = new WindowButtons(Main.Stage);
		toolbar.setRightItems(wb2, wb);
		Button en = addLanguageBtns("en.png");
		Button nl = addLanguageBtns("nl.png");
		toolbar.setLeftItems(en, nl);
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
				loadView("en", "/View/OverView.fxml", "Achmea");
			} else {
				loadView("nl", "/View/OverView.fxml", "Achmea");
			}
		});
		return btn;
	}
}
