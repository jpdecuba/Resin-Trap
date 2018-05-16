package Client.Main;

import Client.FileSave.*;
import Client.HoneyPot.honeyrj.*;
import Shared.Logging.LogConnection;
import Client.HoneyPot.lowinteraction.*;

import Client.HoneyPot.protocol.*;

import Client.Model.*;
import Client.Model.Preset.Preset;
import Client.Sockets.SocketClient;
import Shared.Mail.MailMsg;
import Shared.Model.User;
import Shared.Model.UserRole;
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

import java.util.*;

public class Main extends Application {
    public static Stage Stage;
    private static double xOffset = 0;
    private static double yOffset = 0;
    public static String lang = "en";
    public static JFXToolbar toolbar;
    public static ControllerManager manager;
    //public static LoginModel loginModel;

    public static SocketClient client;

    public static Preferences pref;

    //Honeypot

    public static HoneyRJ honeypot = null;

    //HoneyPot Services

    public static ArrayList<ILIModule> Services = new ArrayList<>();

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
        SavePref();
        if(honeypot != null && GetAccount() != null){
            honeypot.SetDBLog();
        }
    }

    public static boolean SocketCheck(){
        return client.SocketCheck();
    }

    public static User GetAccount() {
        return pref.getAccount();
    }

    public static Preset getPreset() {
        return pref.getPreset();
    }

    public static void setPreset(Preset preset) {
       pref.setPreset(preset);
        SavePref();
    }


    public static Set<LogConnection> GetLogs(){
        return client.GetlogFiles(GetAccount());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        client = new SocketClient(null);
    	//account = null;
		//loginModel = new LoginModel();
        launchHoneypot();
        SaveFiles file = new SaveFiles();
        pref = file.ReadPreferences();
		this.Stage = primaryStage;
		toolbar = new JFXToolbar();
        this.manager = new ControllerManager();
        FXMLLoader loader;
        if(getPreset() == null) {
            manager.currentView = "/Client/View/PresetView.fxml";
            loader = new FXMLLoader(getClass().getResource("/Client/View/PresetView.fxml"));
        }else {
            manager.currentView = "/Client/View/OverView.fxml";
            loader = new FXMLLoader(getClass().getResource("/Client/View/OverView.fxml"));
            getPreset().Start();
        }
        loader.setResources(ResourceBundle.getBundle("Client.bundles.UIResources", new Locale(lang, lang.toUpperCase())));
        Parent root = loader.load();
        this.Stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/Client/Resources/ResinTrap.png")));
        Stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);

        scene.getStylesheets().add("/Client/Resources/style.css");
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto");
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto+Condensed");
        Stage.setTitle("Achmea");
        Stage.setScene(scene);
        Stage.setMinWidth(900);
        Stage.setMinHeight(600);
        ResizeHelper rh = new ResizeHelper();
        rh.addResizeListener(Stage);
		SetupRoot(root);

		/*Main.Stage.setOnHiding(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				if(Main.GetAccount() != null)
				{
					Main.loginModel.Logout(Main.GetAccount().getName());
				}
			}
		});*/


        Stage.show();


        if(honeypot != null && GetAccount() != null){
            honeypot.SetDBLog();
        }
    }


    public static void SendEmail(LogConnection log){
        try {
            if(GetAccount() != null) {
                ArrayList<String> emails = new ArrayList<>();

                //emails.add("dotter5380@gmail.com");
                emails.addAll(GetAccount().getMsgEmail());

                MailMsg msg = new MailMsg(log.getProtocol(), log.getDstIP().toString(), log.getDstPort(), emails);
                client.SendEmail(msg);
            }
        }catch (Exception e){

        }
    }



    public static boolean Register(User urs){
        try {
            return client.Register(urs);
        }catch (Exception e){
            return false;
        }
    }

    public static User Login(User urs){
        try {

            return client.Login(urs);
        }catch (Exception e){
            return null;
        }
    }

    public static boolean AddEmail(String email, int userId)
    {
        try {
            return client.AddEmail(email, userId);
        }catch (Exception e){
            return false;
        }
    }

    public static boolean Logout(User urs){
        try {
            return client.Logout(urs);
        }catch (Exception e){
            return false;
        }
    }

    /*public static boolean RegisterAdmin(User urs){
        try {
            return client.RegisterAdmin(urs);
        }catch (Exception e){
            return false;

        }
    }*/

    public static void SaveLogs(Iterable<LogConnection> logs){
        client.SaveLogs(pref.getAccount(),logs);
    }

    public static void SaveLog(LogConnection log){
        client.SaveLog(pref.getAccount(),log);
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


    public static void StartHoneypotServices(List<ILIModule> list){
        Services.addAll(list);
    }


    public static Status StatusCheck(){
        int start = 0;
        int connections = 0;

        for(ILIModule item : Services){
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
        for(ILIModule item : Services) {
            honeypot.ShutdownService(item);
        }
    }

    public static void SavePref(){

        ArrayList<Integer> ports = new ArrayList<>();
        for(ILIModule item : Services) {
            if(item.OnorOff()){
                ports.add(item.getPort());
            }
            honeypot.ShutdownService(item);

        }

        SaveFiles file = new SaveFiles();
        file.WritePreferences(pref);

        for(ILIModule item : Services){
            honeypot.RegisterService(item);
            if(ports.contains(item.getPort())) {
                honeypot.startPort(item.getPort());
            }
        }
        
    }

    public static boolean CheckForLogout(String buttonText, JFXSnackbar snackbar)
    {
        if(buttonText.equals("LOGOUT"))
        {
            if(Main.Logout(Main.GetAccount()))
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
}
