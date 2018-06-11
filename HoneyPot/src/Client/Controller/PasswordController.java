package Client.Controller;

import Client.Main.Main;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXToolbar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class PasswordController extends BaseController implements Initializable {

	@FXML
	AnchorPane anchor;
	@FXML
	JFXToolbar toolbar;
	@FXML
	JFXPasswordField passwordField;
	@FXML
	JFXPasswordField confirmPasswordField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.manager.setToolbar(this.toolbar);
		snackbar = new JFXSnackbar(anchor);
		Main.manager.currentView = "/Client/View/PasswordView.fxml";
		Main.manager.ChangeNavButtons(loginBtn, adminBtn, settingBtn);
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
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/View/SettingView.fxml"), ResourceBundle.getBundle("Client.Bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
						Parent root = loader.load();
						Main.switchPage(root, "Settings");
					}
					catch (IOException e)
					{
						ErrorMessage("Something went wrong.");
					}
				}
			}
			else
			{
				ErrorMessage("Password is not equal");
			}
		}
		else
		{
			ErrorMessage("Fill in all the fields.");
		}
	}

	private void ErrorMessage(String message)
	{
		snackbar.show(message, 3000);
	}
}
