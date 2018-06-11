package Client.Controller;

import Client.Main.Main;
import Client.Model.Repositories.DatabaseSynchronisation;
import Shared.Model.User;
import Shared.Model.UserRole;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    //Login GUI elements
    @FXML
    AnchorPane anchor;
    @FXML
    JFXToolbar toolbar;
    @FXML
    JFXButton loginBtn;
    @FXML
    JFXButton goToRegisterBtn;
    @FXML
    JFXTextField loginUsernameField;
    @FXML
    JFXPasswordField loginPasswordField;
    @FXML
    AnchorPane menuPane;

    //Register GUI elements
    @FXML
    JFXButton goToLoginBtn;
    @FXML
    JFXButton registerBtn;
    @FXML
    JFXComboBox registerUserRoleField;
    @FXML
    JFXTextField registerUsernameField;
    @FXML
    JFXTextField registerEmailField;
    @FXML
    JFXPasswordField registerPasswordField;
    @FXML
    JFXPasswordField registerConfirmField;
    @FXML
    JFXTextField codeField;
    @FXML
    AnchorPane loadPane;

    //Services GUI elements
    @FXML
    JFXButton overviewBtn;
    @FXML
    JFXButton servicesBtn;

    JFXSnackbar snackbar;
    private DatabaseSynchronisation dbSync = new DatabaseSynchronisation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoadPaneOn();
        if (loginBtn != null) {
            loginBtn.setDefaultButton(true);
        } else {
            registerBtn.setDefaultButton(true);
        }
        Main.manager.setToolbar(this.toolbar);
        snackbar = new JFXSnackbar(anchor);
        if (registerUserRoleField != null) {
            for (UserRole role : UserRole.values()) {
                registerUserRoleField.getItems().add(role);
            }
            registerUserRoleField.getSelectionModel().select(0);
            SetLimiter(registerUsernameField, 50);
            SetLimiter(registerEmailField, 50);
        }
        LoadPaneOff();
    }

    @FXML
    public void changePage(ActionEvent event) {
        LoadPaneOn();
        JFXButton button = (JFXButton) event.getSource();
        String path = "";
        String title = "";

        if (button == loginBtn) {
            if (Login()) {
                if (Main.GetAccount() != null) {
                    snackbar.show("Staring Synchronisation of log files", 1000);

                    try {
                        snackbar.show("Staring Synchronisation of log files", 1000);
                        if (dbSync.SyncLocalAndCloud())
                            snackbar.show("Files Synchronised", 1000);
                        else
                            snackbar.show("File Synchronisation FAILED", 1000);
                    } catch (Exception e) {
                        snackbar.show("File Synchronisation FAILED", 1000);
                    }
                }

                path = "/Client/View/OverView.fxml";
                title = "Overview";
            } else {
                return;
            }
        } else if (button == overviewBtn) {
            path = "/Client/View/OverView.fxml";
            title = "Achmea";
        } else if (button == servicesBtn) {
            path = "/Client/View/ServicesView.fxml";
            title = "Achmea";
        } else if (button == goToRegisterBtn) {
            path = "/Client/View/RegisterView.fxml";
            title = "Register";
        } else if (button == goToLoginBtn) {
            path = "/Client/View/LoginView.fxml";
            title = "Login";
        } else if (button == registerBtn) {
            if (Register()) {
                path = "/Client/view/LoginView.fxml";
                title = "Login";
            } else {
                return;
            }
        }
        String finalPath = path;
        String finalTitle = title;
        LoadPaneOff();
        Platform.runLater(() -> {
            switchPage(finalPath, finalTitle);
        });

    }

    @FXML
    public void ChangeRole() {
        switch ((UserRole) registerUserRoleField.getSelectionModel().getSelectedItem()) {
            case User:
                codeField.setDisable(false);
                break;
            default:
                codeField.setText("");
                codeField.setDisable(true);
                break;
        }
    }

    private void switchPage(String path, String title) {
        try {
            Main.manager.currentView = path;

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path), ResourceBundle.getBundle("Client.Bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
            Parent root = loader.load();
            Main.switchPage(root, title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Login Methode
     *
     * @return boolean
     */
    public boolean Login() {
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();
        User user = Main.Login(new User(username, password));

        if (user != null) {
            Main.setAccount(user);
            return true;
        } else {
            LoadPaneOff();
            snackbar.show("Username or password is wrong", 3000);
        }
        return false;
    }

    /**
     * Register user
     *
     * @return boolean
     */
    public boolean Register() {
        String username = registerUsernameField.getText();
        String password = registerPasswordField.getText();
        String confirm = registerConfirmField.getText();
        String email = registerEmailField.getText();
        String code = codeField.getText();
        UserRole role = (UserRole) registerUserRoleField.getSelectionModel().getSelectedItem();
        if (role == null) {
            snackbar.show("The role chosen is invalid", 3000);
            return false;
        }
        if (!username.trim().equals("") && !password.trim().equals("") && !confirm.trim().equals("") && !email.trim().equals("")) {
            if (!email.contains("@") || !email.contains(".")) {
                snackbar.show("Please fill in a correct email address.", 3000);
                return false;
            }
            if (password.equals(confirm)) {
                User user = null;
                boolean register = false;
                switch (role) {
                    case User:
                        if (code.trim() == "") {
                            snackbar.show("Fill in a code", 3000);
                            return false;
                        }
                        user = new User(username, password, role, code);
                        user.addEmail(email);
                        register = Main.Register(user);
                        break;
                    case Admin:
                        user = new User(username, password, role);
                        user.addEmail(email);
                        register = Main.Register(user);
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
        } else {
            snackbar.show("Please fill in all the fields.", 3000);
            return false;
        }
        return false;
    }

    private void SetLimiter(JFXTextField tf, int max) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (newValue.length() > max) {
                    tf.setText(oldValue);
                }
            }
        });
    }

    public void LoadPaneOn() {
        Platform.runLater(() -> {
            loadPane.setVisible(true);
        });
    }

    public void LoadPaneOff() {
        Platform.runLater(() -> {
            loadPane.setVisible(false);
        });
    }
}
