package Main;

import Controller.OverviewController;
import HoneyPot.honeyrj.HoneyRJ;
import HoneyPot.honeyrj.HoneyRJException;
import HoneyPot.lowinteraction.LIModule;
import HoneyPot.lowinteraction.LIProtocol;
import HoneyPot.protocol.FtpProtocol;
import HoneyPot.protocol.IrcProtocol;
import HoneyPot.protocol.MySQLProtocol;
import HoneyPot.protocol.SmtpProtocol;
import Model.ResizeHelper;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    static Stage Stage;
    private double xOffset = 0;
    private double yOffset = 0;


    //Honeypot

    private static HoneyRJ honeypot = null;

    //HoneyPot Services

    private static LIProtocol ftpP = new FtpProtocol();
    private static LIModule ftpM;
    private static LIProtocol SmtpP = new SmtpProtocol();
    private static LIModule SmtpM;
    private static LIProtocol MysqlP = new MySQLProtocol();
    private static LIModule MysqlM;


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/OverView.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundles.UIResources", new Locale("en", "EN")));
        Parent root = loader.load();
        OverviewController overview = loader.getController();
        this.Stage = primaryStage;
        overview.setStageAndSetupListeners(Stage);
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
        Stage.show();
    }

    public static void switchPage(Parent parent, String title)
    {
        Main.Stage.getScene().setRoot(parent);
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

        honeypot.RegisterService(ftpM);
        honeypot.RegisterService(SmtpM);
        honeypot.RegisterService(MysqlM);
        honeypot.startPort(21);
        honeypot.startPort(25);
        honeypot.startPort(3306);
    }


    public static void StopServices(LIModule module){
        honeypot.DeRegisterService(module);

    }
}
