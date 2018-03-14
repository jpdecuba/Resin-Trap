package Main;

import Controller.OverviewController;
import HoneyPot.honeyrj.HoneyRJ;
import HoneyPot.honeyrj.HoneyRJException;
import HoneyPot.lowinteraction.LIModule;
import HoneyPot.lowinteraction.LIProtocol;
import HoneyPot.protocol.FtpProtocol;
import HoneyPot.protocol.MySQLProtocol;
import HoneyPot.protocol.SmtpProtocol;
import Model.*;
import Model.Database.LoginModel;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXToolbar;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    public static Stage Stage;
    private static double xOffset = 0;
    private static double yOffset = 0;
    public static String lang = "en";
    public static JFXToolbar toolbar;
    public static ControllerManager manager;
    public static User account;
    public static LoginModel loginModel;

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

    //Status

    private static Status status;

    public static int ConnectionAlert = 5;


    @Override
    public void start(Stage primaryStage) throws Exception {
    	account = null;
		loginModel = new LoginModel();
        launchHoneypot();
        StartHoneypotServices();
		this.Stage = primaryStage;
		toolbar = new JFXToolbar();
        this.manager = new ControllerManager();
		manager.currentView = "/View/OverView.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/OverView.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundles.UIResources", new Locale(lang, lang.toUpperCase())));
        Parent root = loader.load();
        OverviewController Overview = loader.getController();
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
		Main.Stage.setOnHiding(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				if(Main.account != null)
				{
					Main.loginModel.Logout(Main.account.getName());
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


    public static void StartHoneypotServices(){
        MysqlM = new LIModule(MysqlP,honeypot);
        SmtpM = new LIModule(SmtpP,honeypot);
        ftpM = new LIModule(ftpP,honeypot);

        Services.add(MysqlM);
        Services.add(SmtpM);
        Services.add(ftpM);
        for(LIModule item : Services){
            honeypot.RegisterService(item);
            honeypot.startPort(item.getPort());
        }
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
            if(Main.loginModel.Logout(Main.account.getName()))
            {
                Main.account = null;
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
