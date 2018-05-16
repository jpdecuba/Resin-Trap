package Client.Controller;

import Client.Main.Main;
import Shared.Model.UserRole;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToolbar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsController extends BaseController implements Initializable {

	@FXML
	AnchorPane anchor;
	@FXML
	JFXToolbar toolbar;
	@FXML
	TextField keylbl;
	@FXML
	ListView<String> emailList;
	@FXML
	JFXButton addEmail;
	@FXML
	JFXTextField addEmailField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.manager.setToolbar(this.toolbar);
		snackbar = new JFXSnackbar(anchor);
		Main.manager.ChangeNavButtons(loginBtn, adminBtn, settingBtn);
		keylbl.setEditable(false);
		if(Main.GetAccount() != null)
		{
			emailList.getItems().addAll(Main.GetAccount().getMsgEmail());
			if(Main.GetAccount().getRole() == UserRole.Admin)
			{
				keylbl.setDisable(false);
				keylbl.setText("Key: " + Main.GetAccount().getCode());
				keylbl.setEditable(false);
				keylbl.setStyle("-fx-text-box-border: transparent; -fx-background-color: transparent;");
			}
		}
		else
		{
			addEmail.setDisable(true);
			keylbl.setDisable(true);
		}

		addEmailField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (newValue.length() > 50) {
					addEmailField.setText(oldValue);
				}
			}
		});
	}

	@FXML
	public void addEmail()
	{
		String email = addEmailField.getText();
		if(!email.trim().equals("") && !email.contains("@") && !email.contains("."))
		{
			snackbar.show("Please fill in a correct email address.",3000);
		}
		else
		{
			if(Main.AddEmail(email, Main.GetAccount().getId()))
			{
				Main.GetAccount().addEmail(email);
				Main.setAccount(Main.GetAccount() );
				emailList.getItems().clear();
				emailList.getItems().addAll(Main.GetAccount().getMsgEmail());
				addEmailField.setText("");
			}
			else
			{
				snackbar.show("Something went wrong while adding your Email.",3000);
			}
		}
	}

	public void changePassw() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/View/PasswordView.fxml"), ResourceBundle.getBundle("Client/bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
			Parent root = loader.load();
			Main.switchPage(root, "Change Password");
		}
		catch(IOException e) {
			snackbar.show("Something went wrong.", 3000);
		}
	}

	@FXML
	public void deleteEmail()
	{
		String email = emailList.getSelectionModel().getSelectedItem();
		if(email == null)
		{
			snackbar.show("Please select an email to delete",3000);
		} else {
			if (Main.client.DeleteEmail(email, Main.GetAccount().getId())) {
				Main.GetAccount().getMsgEmail().remove(email);
				Main.setAccount(Main.GetAccount());
				emailList.getItems().remove(email);
			} else {
				snackbar.show("Something went wrong while deleting your Email.", 3000);
			}
		}
	}
}
