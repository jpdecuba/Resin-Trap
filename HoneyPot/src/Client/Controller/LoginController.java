package Client.Controller;

import Client.Main.Main;
import Client.Model.User;
import Client.Model.UserRole;
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
	@FXML JFXComboBox registerUserRoleField;
    @FXML JFXTextField registerUsernameField;
    @FXML JFXPasswordField registerPasswordField;
    @FXML JFXPasswordField registerConfirmField;
    @FXML JFXTextField codeField;

    //Services GUI elements
	@FXML JFXButton overviewBtn;
	@FXML JFXButton servicesBtn;

    JFXSnackbar snackbar;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.manager.setToolbar(this.toolbar);
        snackbar = new JFXSnackbar(anchor);
        if(registerUserRoleField != null) {
			for (UserRole role : UserRole.values()) {
				registerUserRoleField.getItems().add(role);
			}
			registerUserRoleField.getSelectionModel().select(0);
		}
	}

	@FXML
	public void changePage(ActionEvent event) {
		JFXButton button = (JFXButton) event.getSource();
		String path = "";
		String title = "";

		if(button == loginBtn)
		{
			if(Login()) {
				path = "/Client/View/OverView.fxml";
				title = "Overview";
			}
			else
			{
				return;
			}
		}
		else if (button == overviewBtn){
			path = "/Client/View/OverView.fxml";
			title = "Achmea";
		}
		else if (button == servicesBtn){
			path = "/Client/View/ServicesView.fxml";
			title = "Achmea";
		}
		else if (button == goToRegisterBtn)
		{
			path = "/Client/view/RegisterView.fxml";
			title = "Register";
		}
		else if (button == goToLoginBtn)
		{
			path = "/Client/view/LoginView.fxml";
			title = "Login";
		}
		else if (button == registerBtn)
		{
			if(Register())
			{
				path = "/Client/view/LoginView.fxml";
				title = "Login";
			}
			else
			{
				return;
			}
		}
		switchPage(path, title);
	}

	@FXML
	public void ChangeRole()
	{
		switch ((UserRole)registerUserRoleField.getSelectionModel().getSelectedItem())
		{
			case User:
				codeField.setDisable(false);
				break;
			default:
				codeField.setText("");
				codeField.setDisable(true);
				break;
		}
	}

	private void switchPage(String path, String title)
	{
		try
		{
			Main.manager.currentView = path;

			FXMLLoader loader = new FXMLLoader(getClass().getResource(path), ResourceBundle.getBundle("Client.bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
			Parent root = loader.load();
			Main.switchPage(root, title);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Login Methode
	 * @return boolean
	 */
    public boolean Login(){
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();
		User user = Main.loginModel.Login( new User(username, password));

        if(user != null) {
			Main.setAccount(user);

            return true;
        }else {
            snackbar.show("Username or password is wrong",3000);
        }
        return false;
    }

	/**
	 * Register user
	 * @return boolean
	 */
    public boolean Register() {
		String username = registerUsernameField.getText();
		String password = registerPasswordField.getText();
		String confirm = registerConfirmField.getText();
		String code = codeField.getText();
		UserRole role = (UserRole) registerUserRoleField.getSelectionModel().getSelectedItem();
		if(role == null)
		{
			snackbar.show("The role chosen is invalid",3000);
			return false;
		}

		if (password.equals(confirm)) {
			User user = null;
			boolean register = false;
			switch (role)
			{
				case User:
					if(code.trim() == "")
					{
						snackbar.show("Fill in a code", 3000);
						return false;
					}
					user = new User(username, password, role, code);
					register = Main.loginModel.Register(user);
					break;
				case Admin:
					user = new User(username, password, role);
					register = Main.loginModel.RegisterAdmin(user);
					break;
			}
			if (register) {

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
