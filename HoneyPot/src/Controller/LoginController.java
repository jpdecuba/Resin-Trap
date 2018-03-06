package Controller;

import Main.Main;
import Model.Database.LoginModel;
import Model.User;
import Model.WindowButtons;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToolbar;
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

    Stage stage;

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
				String lang = "en";
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/OverView.fxml"), ResourceBundle.getBundle("bundles.UIResources", new Locale(lang, lang.toUpperCase())));
				Parent root = loader.load();
				OverviewController overviewCon = loader.getController();
				overviewCon.setStageAndSetupListeners(this.stage);
				Main.switchPage(root, "Login");
			}
		}
		else if (button == registerBtn)
		{

		}
	}

    public void setStageAndSetupListeners(Stage stage) {
        this.stage = stage;
        WindowButtons wb = new WindowButtons();
        WindowButtons wb2 = new WindowButtons(anchor);
        toolbar.setRightItems(wb2, wb);
        Button en = addLanguageBtns("en.png");
        Button nl = addLanguageBtns("nl.png");
        toolbar.setLeftItems(en, nl);
    }

    private void loadView(String lang) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/LoginView.fxml"), ResourceBundle.getBundle("bundles.UIResources", new Locale(lang, lang.toUpperCase())));
            Parent root = loader.load();
            LoginController loginCon = loader.getController();
            loginCon.setStageAndSetupListeners(this.stage);
            Main.switchPage(root, "Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                if (lang.equals("en.png")) {
                    loadView("en");
                } else {
                    loadView("nl");
                }
            }
        });
        return btn;
    }
}
