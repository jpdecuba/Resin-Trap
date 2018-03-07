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

import java.io.IOException;
import java.net.URL;
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
    Stage stage;
    String language = "en";
    String nameView = "/view/LoginView.fxml";
    String title = "placeholder title";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginModel = new LoginModel();
	}

	public void GoToRegister(){
	    nameView = "/view/RegisterView.fxml";
	    title = "Register";
	    loadView(language);
    }

    public void GoToLogin()  {
	    nameView = "/view/LoginView.fxml";
        title = "Login";
        loadView(language);
    }

    public void GoToOverview(){
        nameView = "/view/OverviewView.fxml";
        title = "Overview";
        loadView(language);
    }

    public void Login(){
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();

        User user = new User(username, password);

        if(loginModel.Login( user) != null) {
            GoToOverview();
        }else {
            Snack
        }
    }

    public void Register()  {
        String username = registerUsernameField.getText();
        String password = registerPasswordField.getText();
        String confirm = registerConfirmField.getText();

        if(password.equals(confirm)){
            User user = new User(username, password);
            if(loginModel.Register(user)){
                GoToLogin();
            }
        }
    }

    public void setStageAndSetupListeners(Stage stage) {
        this.stage = stage;
        WindowButtons wb = new WindowButtons();
        WindowButtons wb2 = new WindowButtons(stage);
        toolbar.setRightItems(wb2, wb);
        Button en = addLanguageBtns("en.png");
        Button nl = addLanguageBtns("nl.png");
        toolbar.setLeftItems(en, nl);
    }

    private void loadView(String lang) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nameView), ResourceBundle.getBundle("bundles.UIResources", new Locale(lang, lang.toUpperCase())));
            Parent root = null;
            root = loader.load();
            LoginController loginCon = loader.getController();
            loginCon.setStageAndSetupListeners(this.stage);
            Main.switchPage(root, title);
        } catch (IOException ioe) {
            ioe.printStackTrace();
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
                    language = "en";
                } else {
                    language = "nl";
                }
                loadView(language);
            }
        });
        return btn;
    }
}
