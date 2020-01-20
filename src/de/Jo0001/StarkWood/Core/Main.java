package de.Jo0001.StarkWood.Core;

import de.Jo0001.StarkWood.Downloader.UpdateCheck;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        UpdateCheck updateCheck = new UpdateCheck();
        updateCheck.start();
        FXMLLoader loader = new FXMLLoader();
        final Controller controller = loader.getController();
        loader.setController(controller);

        Parent root = loader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("StarkWood");
        primaryStage.setScene(new Scene(root, 310, 235));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/fxml/style.css").toExternalForm());
        primaryStage.show();

    }

    public static void sendTray(String text) throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();
        java.awt.Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/icon.png"));
        TrayIcon trayIcon = new TrayIcon(image, text);
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("StarkWood");
        tray.add(trayIcon);
        trayIcon.displayMessage("StarkWood", text, TrayIcon.MessageType.INFO);
    }

}