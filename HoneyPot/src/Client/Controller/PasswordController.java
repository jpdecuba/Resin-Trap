package Client.Controller;

import Client.Main.Main;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXToolbar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class PasswordController extends BaseController implements Initializable {

	@FXML
	JFXToolbar toolbar;
	@FXML
	JFXPasswordField passwordField;
	@FXML
	JFXPasswordField confirmPasswordField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.manager.setToolbar(this.toolbar);
		Main.manager.currentView = "/Client/View/Password.fxml";
	}
}
