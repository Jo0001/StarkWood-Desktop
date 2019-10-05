package de.Jo0001.StarkWood.Core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class Main extends Application {
    private static HttpURLConnection con;
    static final FXMLLoader loader = new FXMLLoader();

    //TODO Add error Alerts

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = loader.getController();
        loader.setController(controller);
      /* LoadingController loadingController =loader.getController();
       loader.setController(loadingController);*/

        Parent root = loader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setTitle("StarkWood");
        primaryStage.setScene(new Scene(root, 310, 235));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/fxml/style.css").toExternalForm());
        primaryStage.show();
    }

    public static void getInfo() throws IOException, AWTException {
        System.out.println("Laden . . .");
        final URL myurl = new URL("https://www.dropbox.com/s/h3kvtvzy9i5kmr4/infos.starkwood?dl=1");
        con = (HttpURLConnection) myurl.openConnection();
        con.setDoOutput(true);
        String[] infos = new String[8];
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            for (int i = 0; i < infos.length; i++) {
                infos[i] = in.readLine();
            }
            con.disconnect();
        } catch (UnknownHostException e) {
            System.err.println("Keine Netzwerkverbindung!");
            alert("Keine Netzwerkverbindung :(");
            e.printStackTrace();
        }

        updateCheck(infos[7]);
    }

    public static void updateCheck(String ver) throws AWTException {
        final String cVer = "1.1";
        if (cVer.equalsIgnoreCase(ver)) {
            System.out.println("Aktuell");
        } else {
            System.err.println("Veraltete Version, bitte downloade die neuste Version dieses Tools");
            alert("Veraltete Version, bitte downloade die neuste Version dieses Tools");
            System.exit(-99);
        }


    }

    public static void alert( String text) throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();
        java.awt.Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/icon.png"));
        TrayIcon trayIcon = new TrayIcon(image, text);
        // Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        // Set tooltip text for the tray icon (in der Taskleiste)
        trayIcon.setToolTip("StarkWood");
        tray.add(trayIcon);
        // Alternativ MessageType.Info
        trayIcon.displayMessage("StarkWood", text, TrayIcon.MessageType.INFO);
    }

    public static void main(String[] args) {
        Application.launch(Main.class);
    }
}
