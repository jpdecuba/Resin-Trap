package Controller;

import Main.Main;
import Model.Database.LoginModel;
import Model.User;
import Model.WindowButtons;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToolbar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    AnchorPane anchor;
    @FXML
    JFXToolbar toolbar;
    @FXML
    JFXButton loginBtn;
    @FXML
    JFXButton registerBtn;
    @FXML
	JFXTextField loginUsernameField;
    @FXML
	JFXPasswordField loginPasswordField;
    @FXML
    AnchorPane menuPane;

    LoginModel loginModel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginModel = new LoginModel();
	}

	@FXML
	public void btnClick(ActionEvent e) throws IOException {
		JFXButton button = (JFXButton) e.getSource();
		if (button == loginBtn)
		{
			if(loginModel.Login(new User(loginUsernameField.getText(), loginPasswordField.getText())) != null)
			{
				Platform.runLater(() -> {
					try {
					String lang = "en";
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/OverView.fxml"), ResourceBundle.getBundle("bundles.UIResources", new Locale(lang, lang.toUpperCase())));
					Parent root = null;
					root = loader.load();
					OverviewController overviewCon = loader.getController();
					//overviewCon.setStageAndSetupListeners();
					Main.switchPage(root, "Achmea");
					}
					 catch (IOException e1) {
						e1.printStackTrace();
					}
				});
			}
		}
		else if (button == registerBtn)
		{

		}
	}

    public void ChangeView()  {
        throw new NotImplementedException();
    }

    public void Register()  {
        throw new NotImplementedException();
    }
}
