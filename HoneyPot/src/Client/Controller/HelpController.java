package Client.Controller;

import Client.Main.Main;
import com.jfoenix.controls.JFXToolbar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class HelpController extends BaseController implements Initializable {
    @FXML
    JFXToolbar toolbar;
    @FXML
    Hyperlink linksys;
    @FXML
    Hyperlink netgear;
    @FXML
    Hyperlink ddwrt;
    @FXML
    Hyperlink tplink;
    @FXML
    Hyperlink asus;
    @FXML
    VBox box;
    @FXML
    Label os;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.manager.setToolbar(this.toolbar);
        Main.manager.ChangeNavButtons(loginBtn, adminBtn, settingBtn);
    }

    public void setup(boolean isWindows) {
        if (isWindows) {
            os.setText("Windows");
            ArrayList<Node> nodeList = new ArrayList();
            for (Node node : box.getChildren()) {
                if (node.getId() != null) {
                    if (node.getId().contains("mac")) {
                        nodeList.add(node);
                    }
                }
            }
            box.getChildren().removeAll(nodeList);
        } else {
            os.setText("Mac OS X");
            ArrayList<Node> nodeList = new ArrayList();
            for (Node node : box.getChildren()) {
                if (node.getId() != null) {
                    if (node.getId().contains("win")) {
                        nodeList.add(node);
                    }
                }
            }
            box.getChildren().removeAll(nodeList);
        }
    }

    public void back(){
        try {
            String path = "/Client/View/OSView.fxml";
            String title = "Achmea";
            Main.manager.currentView = path;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path), ResourceBundle.getBundle("Client.Bundles.UIResources", new Locale(Main.lang, Main.lang.toUpperCase())));
            Parent root = loader.load();
            Main.switchPage(root, title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void OpenLink(ActionEvent source) {
        if (Desktop.isDesktopSupported()) {
            try {
                Hyperlink link = (Hyperlink) source.getSource();
                if (link == linksys) {
                    Desktop.getDesktop().browse(new URI("https://www.expressvpn.com/support/troubleshooting/linksys-block-ip-address/"));
                } else if (link == netgear) {
                    Desktop.getDesktop().browse(new URI("https://www.expressvpn.com/support/troubleshooting/netgear-block-ip-address/"));
                } else if (link == ddwrt) {
                    Desktop.getDesktop().browse(new URI("https://www.expressvpn.com/support/troubleshooting/dd-wrt-block-ip-address/"));
                } else if (link == tplink) {
                    Desktop.getDesktop().browse(new URI("https://www.expressvpn.com/support/troubleshooting/tp-link-block-ip-address/"));
                } else if (link == asus) {
                    Desktop.getDesktop().browse(new URI("https://www.expressvpn.com/support/troubleshooting/asus-block-ip-address/"));
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }
}
