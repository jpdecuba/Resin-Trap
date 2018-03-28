package Model;

import Main.Main;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class WindowButtons extends HBox {

    boolean fullscreen;
    MaterialDesignIconView fa;
    MaterialDesignIconView fa2;

    public WindowButtons() {
        Button closeBtn = new Button("");
        this.setStyle("-fx-alignment: center");
        closeBtn.setMaxSize(25, 25);
        closeBtn.setMinSize(25, 25);
        closeBtn.setPrefSize(25, 25);
        MaterialDesignIconView fa = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_CLOSE);
        fa.setFill(Color.valueOf("#c15683"));
        fa.setSize("18");
        closeBtn.setGraphic(fa);
        closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: achmeaPink; -fx-alignment: bottom-center");
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                MinimizeToTray();

                //Main.Stage.(false);
                //Main.Shutdown();
                //System.exit(0);
            }
        });
        this.getChildren().add(closeBtn);

    }

    public WindowButtons(Stage stage) {
        Button maxBtn = new Button("");
        this.setStyle("-fx-alignment: center");
        maxBtn.setMaxSize(25, 25);
        maxBtn.setMinSize(25, 25);
        maxBtn.setPrefSize(25, 25);
        fa = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_MAXIMIZE);
        fa2 = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_RESTORE);
        fa.setFill(Color.valueOf("#c15683"));
        fa2.setFill(Color.valueOf("#c15683"));
        maxBtn.setGraphic(fa);
        fa.setSize("18");
        fa2.setSize("18");

        fullscreen = false;

        maxBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: achmeaPink; -fx-alignment: baseline-center");
        maxBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                if (!fullscreen) {
                    fullscreen = true;
                    stage.setMaximized(true);
                    maxBtn.setGraphic(fa2);
                }
                else {
                    fullscreen = false;
                    stage.setMaximized(false);
                    maxBtn.setGraphic(fa);
                }
            }
        });

        this.getChildren().add(maxBtn);
    }

    private void MinimizeToTray(){
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        Platform.setImplicitExit(false);
        Platform.runLater(new Runnable(){
            public void run() {
                hideStage();
            }
        });

        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon =
                new TrayIcon(createImage("/resources/duckytiny.png", "tray icon"));
        final SystemTray tray = SystemTray.getSystemTray();
        //trayIcon.setImageAutoSize(true);

        MenuItem maximizeItem = new MenuItem("Maximize");
        MenuItem turnOffItem = new MenuItem("Turn off honeypot");
        MenuItem turnOnItem = new MenuItem("Turn on honeypot");
        MenuItem exitItem = new MenuItem("Exit");

        popup.add(maximizeItem);
        //popup.add(turnOffItem);
        //popup.add(turnOnItem);
        popup.add(exitItem);

        //turnOnItem.setEnabled(false);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        maximizeItem.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                showStage();
                tray.remove(trayIcon);
            }
        });

        //turnOffItem.addActionListener(new ActionListener() {
        //    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        //        Main.Shutdown();
        //        turnOnItem.setEnabled(true);
        //        turnOffItem.setEnabled(false);
        //    }
        //});

        //turnOffItem.addActionListener(new ActionListener() {
        //    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        //        ();
        //        turnOnItem.setEnabled(true);
        //        turnOffItem.setEnabled(false);
        //    }
        //});

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                tray.remove(trayIcon);
                Main.Shutdown();
                System.exit(0);
            }
        });

        trayIcon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && !mouseEvent.isConsumed()) {
                    mouseEvent.consume();
                    showStage();
                    tray.remove(trayIcon);
                }

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    private void showStage() {
        if (Main.Stage != null) {
            Platform.setImplicitExit(false);
            Platform.runLater(new Runnable(){
                public void run() {
                    Main.Stage.show();
                    Main.Stage.toFront();
                }
            });
        }
    }

    private void hideStage() {
        if (Main.Stage != null) {
            Main.Stage.hide();
        }
    }

    //Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = WindowButtons.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
