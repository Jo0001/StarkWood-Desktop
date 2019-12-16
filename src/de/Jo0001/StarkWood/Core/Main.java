package de.Jo0001.StarkWood.Core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

public class Main extends Application {
    private static HttpURLConnection con;
    static final FXMLLoader loader = new FXMLLoader();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = loader.getController();
        loader.setController(controller);

        Parent root = loader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("StarkWood");
        primaryStage.setScene(new Scene(root, 310, 235));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/fxml/style.css").toExternalForm());
        primaryStage.show();
        getInfo();
    }

    public void getInfo() throws IOException {
        System.out.println("Checking for updates...");
        final URL myurl = new URL("https://www.dropbox.com/s/h3kvtvzy9i5kmr4/infos.starkwood?dl=1");
        con = (HttpURLConnection) myurl.openConnection();
        con.setDoOutput(true);
        String[] infos = new String[8];
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            for (int i = 0; i < infos.length; i++) {
                infos[i] = in.readLine();
            }
            con.disconnect();
        } catch (UnknownHostException | SocketTimeoutException e) {
            System.err.println("No network connection!");
            e.printStackTrace();
            localAlert("Keine Netzwerkverbindung!");
            System.exit(-500);
        }
        final String cVer = "1.2";
        if (cVer.equalsIgnoreCase(infos[7])) {
            System.out.println(cVer+" is already the newest version");
        } else {
            System.err.println("Outdated version, please download the new "+infos[7]+"version");
            localAlert("Veraltete Version, bitte downloade die neuste Version dieses Tools");
            System.exit(-99);
        }
    }

    public void localAlert(String mes){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mes);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        alert.showAndWait();
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

    public static void main(String[] args) {
        Application.launch(Main.class);
    }
}
