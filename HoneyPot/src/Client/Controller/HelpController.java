package Client.Controller;

import Client.Main.Main;
import com.jfoenix.controls.JFXToolbar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpController extends BaseController implements Initializable {
    @FXML JFXToolbar toolbar;
    @FXML Hyperlink linksys;
    @FXML Hyperlink netgear;
    @FXML Hyperlink ddwrt;
    @FXML Hyperlink tplink;
    @FXML Hyperlink asus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.manager.setToolbar(this.toolbar);
    }

    @FXML
    public void OpenLink(ActionEvent source) {
        if(Desktop.isDesktopSupported())
        {
            try {
                Hyperlink link = (Hyperlink) source.getSource();
                if (link == linksys){
                    Desktop.getDesktop().browse(new URI("https://www.expressvpn.com/support/troubleshooting/linksys-block-ip-address/"));
                } else if (link == netgear){
                    Desktop.getDesktop().browse(new URI("https://www.expressvpn.com/support/troubleshooting/netgear-block-ip-address/"));
                } else if (link == ddwrt){
                    Desktop.getDesktop().browse(new URI("https://www.expressvpn.com/support/troubleshooting/dd-wrt-block-ip-address/"));
                } else if (link == tplink){
                    Desktop.getDesktop().browse(new URI("https://www.expressvpn.com/support/troubleshooting/tp-link-block-ip-address/"));
                } else if (link == asus){
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
