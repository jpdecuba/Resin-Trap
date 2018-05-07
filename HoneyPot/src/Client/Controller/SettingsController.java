package Client.Controller;

import Client.Main.Main;
import Client.Model.UserRole;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXToolbar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController extends BaseController implements Initializable {

	@FXML
	AnchorPane anchor;
	@FXML
	JFXToolbar toolbar;
	@FXML
	TextField keylbl;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.manager.setToolbar(this.toolbar);
		Main.manager.currentView = "/Client/View/Settings.fxml";
		snackbar = new JFXSnackbar(anchor);
		Main.ChangeLoginButton(loginBtn);
		Main.ChangeAdminButton(adminBtn);
		if(Main.GetAccount().getRole() == UserRole.Admin)
		{
			keylbl.setText("Key: " + Main.GetAccount().getCode());
			keylbl.setEditable(false);
			keylbl.setStyle("-fx-text-box-border: transparent; -fx-background-color: transparent;");
		}
		else
		{
			keylbl = null;
		}
	}


}
