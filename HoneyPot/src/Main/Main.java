package Main;

import FileSave.Preferences;
import FileSave.SaveFiles;
import HoneyPot.honeyrj.HoneyRJ;
import HoneyPot.honeyrj.HoneyRJException;
import HoneyPot.lowinteraction.LIModule;
import HoneyPot.lowinteraction.LIProtocol;
import HoneyPot.protocol.*;
import Model.*;
import Model.Database.LoginModel;
import Model.Preset.Preset;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXToolbar;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    public static Stage Stage;
    private static double xOffset = 0;
    private static double yOffset = 0;
    public static String lang = "en";
    public static JFXToolbar toolbar;
    public static ControllerManager manager;
    public static LoginModel loginModel;

    public static Preferences pref;

    //Honeypot

    public static HoneyRJ honeypot = null;

    //HoneyPot Services

    public static ArrayList<LIModule> Services = new ArrayList<>();

    private static LIProtocol ftpP = new FtpProtocol();
    public static LIModule ftpM;
    private static LIProtocol SmtpP = new SmtpProtocol();
    private static LIModule SmtpM;
    private static LIProtocol MysqlP = new MySQLProtocol();
    private static LIModule MysqlM;

    private static LIProtocol blank = new BlankProtocol(9022);
    private static LIModule blankm;

    //Status

    private static Status status;

    public static int ConnectionAlert = 5;


    public static void setAccount(User account) {
        pref.setAccount(account);
    }

    public static User GetAccount() {
        return pref.getAccount();
    }

    public static Preset getPreset() {
        return pref.getPreset();
    }

    public static void setPreset(Preset preset) {
       pref.setPreset(preset);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	//account = null;
		loginModel = new LoginModel();
        launchHoneypot();
		this.Stage = primaryStage;
		toolbar = new JFXToolbar();
        this.manager = new ControllerManager();
		manager.currentView = "/View/PresetView.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PresetView.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundles.UIResources", new Locale(lang, lang.toUpperCase())));
        Parent root = loader.load();
        this.Stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/Resources/Ducky.png")));
        Stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto");
        scene.getStylesheets().add("Resources/style.css");
        Stage.setTitle("Achmea");
        Stage.setScene(scene);
        Stage.setMinWidth(900);
        Stage.setMinHeight(600);
        ResizeHelper rh = new ResizeHelper();
        rh.addResizeListener(Stage);
		SetupRoot(root);
        SaveFiles file = new SaveFiles();
        pref = file.ReadPreferences();
		Main.Stage.setOnHiding(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				if(Main.GetAccount() != null)
				{
					Main.loginModel.Logout(Main.GetAccount().getName());
				}
			}
		});


        Stage.show();
    }

    public static void switchPage(Parent parent, String title)
    {
        Main.Stage.getScene().setRoot(parent);
		SetupRoot(Main.Stage.getScene().getRoot());
        Main.Stage.setTitle(title);
        Main.Stage.show();
    }

    public static void launchHoneypot(){

        try {
            honeypot = new HoneyRJ();
        } catch (HoneyRJException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void StartHoneypotServices(List<LIModule> list){
        Services.addAll(list);
    }


    public static Status StatusCheck(){
        int start = 0;
        int connections = 0;

        for(LIModule item : Services){
            if(item.isStarted()){
                start++;
            }
            connections += item.getNumberOfActiveConnections();
        }
        if(start == 0){
            return status.OFF;
        }

        if(connections > ConnectionAlert){
            return status.ALERT;
        }



        return status.OK;
    }

    public static void Shutdown(){
        for(LIModule item : Services) {
            honeypot.DeRegisterService(item);
        }
    }

    public static boolean CheckForLogout(String buttonText, JFXSnackbar snackbar)
    {
        if(buttonText.equals("LOGOUT"))
        {
            if(Main.loginModel.Logout(Main.GetAccount().getName()))
            {
                Main.setAccount(null);
                return true;
            }
            else
            {
                snackbar.show("Failed to logout", 3000);
				return false;
            }
        }
        return true;
    }

    public static void SetupRoot(Parent root)
	{
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (yOffset <= 40) {
					Stage.setX(event.getScreenX() - xOffset);
					Stage.setY(event.getScreenY() - yOffset);
				}
			}
		});
	}

	public static void ChangeLoginButton(JFXButton button)
	{
		if(Main.GetAccount() != null)
		{
			button.setText(ResourceBundle.getBundle("bundles.UIResources",new Locale(Main.lang.toUpperCase())).getString("logout"));
		}
	}

//
//	public static void ChangeButtons(String view, String title)
//	{
//		for (int i = 0; i < toolbar.getLeftItems().size(); i++)
//		{
//			Button btn = (Button)toolbar.getLeftItems().get(i);
//			btn.setOnAction(actionEvent -> {
//				if (lang.equals("en.png")) {
//					manager.loadView("en", view, title);
//				} else {
//					manager.loadView("nl", view, title);
//				}
//			});
//		}
//	}
}
