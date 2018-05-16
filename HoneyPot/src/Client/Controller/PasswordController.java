package Client.Controller;

import Client.Main.Main;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXToolbar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
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

	@FXML
	public void changePassword()
	{
		String passw = passwordField.getText();
		String passwConfirm = confirmPasswordField.getText();
		if(passw.length() > 0 && passwConfirm.length() > 0)
		{
			if(passw.equals(passwConfirm))
			{
				if(Main.ChangePassword(passw, Main.GetAccount().getId()))
				{
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/View/SettingView.fxml"), ResourceBundle.getBundle("Client/bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
						Parent root = loader.load();
						Main.switchPage(root, "Settings");
					}
					catch (IOException e)
					{
						snackbar.show("Something went wrong.", 3000);
					}
				}
			}
			else
			{
				snackbar.show("Password is not equal", 3000);
			}
		}
		else
		{
			snackbar.show("Fill in all the fields.", 3000);
		}
	}
}
