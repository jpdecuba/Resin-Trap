package Controller;

import Main.Main;
import Model.Database.LoginModel;
import Model.User;
import Model.WindowButtons;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.registry.Registry;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    //Login GUI elements
    @FXML AnchorPane anchor;
    @FXML JFXToolbar toolbar;
    @FXML JFXButton loginBtn;
    @FXML JFXButton goToRegisterBtn;
    @FXML JFXTextField loginUsernameField;
    @FXML JFXPasswordField loginPasswordField;
    @FXML AnchorPane menuPane;

    //Register GUI elements
    @FXML JFXButton goToLoginBtn;
    @FXML JFXButton registerBtn;
    @FXML JFXTextField registerUsernameField;
    @FXML JFXPasswordField registerPasswordField;
    @FXML JFXPasswordField registerConfirmField;

    LoginModel loginModel;
    JFXSnackbar snackbar;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginModel = new LoginModel();
		Main.manager.setToolbar(this.toolbar);
        snackbar = new JFXSnackbar(anchor);
	}

	@FXML
	public void changePage(ActionEvent event) {
		JFXButton button = (JFXButton) event.getSource();
		String path = "";
		String title = "";

		if(button == loginBtn)
		{
			if(Login()) {
				path = "/View/OverView.fxml";
				title = "Overview";
			}
			else
			{
				return;
			}
		}
		else if (button == goToRegisterBtn)
		{
			path = "/view/RegisterView.fxml";
			title = "Register";
		}
		else if (button == goToLoginBtn)
		{
			path = "/view/LoginView.fxml";
			title = "Login";
		}
		else if (button == registerBtn)
		{
			if(Register())
			{
				path = "/view/LoginView.fxml";
				title = "Login";
			}
			else
			{
				return;
			}
		}
		switchPage(path, title);
	}

	private void switchPage(String path, String title)
	{
		try
		{
			Main.manager.currentView = path;

			FXMLLoader loader = new FXMLLoader(getClass().getResource(path), ResourceBundle.getBundle("bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
			Parent root = loader.load();
			Main.switchPage(root, title);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

    public boolean Login(){
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();

        User user = new User(username, password);

        if(loginModel.Login( user) != null) {
            return true;
        }else {
            snackbar.show("Username or password is wrong",3000);
        }
        return false;
    }

    public boolean Register() {
		String username = registerUsernameField.getText();
		String password = registerPasswordField.getText();
		String confirm = registerConfirmField.getText();

		if (password.equals(confirm)) {
			User user = new User(username, password);
			if (loginModel.Register(user)) {

				return true;

			} else {
				snackbar.show("Failed", 3000);
			}
		} else {
			snackbar.show("Password is not equal", 3000);
		}
		return false;
	}
}
