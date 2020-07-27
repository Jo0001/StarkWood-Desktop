package de.Jo0001.StarkWood.Downloader;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Optional;


public class UpdateCheck extends Thread {
    public void run() {
        try {
            getInfo();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void getInfo() throws IOException, ParseException {
        System.out.println("Checking for updates...");
        final URL myurl = new URL("https://www.dropbox.com/s/7jweb1pqmza0twk/data.json?dl=1");
        try {
            HttpURLConnection con = (HttpURLConnection) myurl.openConnection();
            con.setDoOutput(true);
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            JSONParser parser = new JSONParser();
            Object object = parser.parse(bufReader);
            con.disconnect();
            JSONObject json = (JSONObject) object;
            JSONArray hashlist = (JSONArray) json.get("hashlist");

            //Store the hash values for later
            Store.hash1 = hashlist.get(0).toString();
            Store.hash2 = hashlist.get(1).toString();
            Store.hash3 = hashlist.get(2).toString();

            final double currentVersion = 1.6;//note also change version in main.fxml

            if (currentVersion == Double.parseDouble(json.get("version").toString())) {
                System.out.println(currentVersion + " is already the latest version");
            } else if (currentVersion > Double.parseDouble(json.get("version").toString())) {
                System.out.println("Wow thats a newer version than released");
            } else {
                System.err.println("Outdated version, please download the new " + json.get("version") + " version");
                localAlert("StarkWood - Veraltete Version", "Veraltete Version, klicke auf OK um die Downloadseite zu öffnen", Alert.AlertType.CONFIRMATION);
            }
        } catch (UnknownHostException | SocketTimeoutException e) {
            System.err.println("No network connection!");
            e.printStackTrace();
            localAlert("StarkWood - Keine Netzwerkverbindung", "Keine Netzwerkverbindung! Bitte überprüfe deine Verbindung", Alert.AlertType.ERROR);
        }

    }

    private static void localAlert(String title, String mes, Alert.AlertType type) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            if (type == Alert.AlertType.CONFIRMATION) {
                alert.setGraphic(null);
            }
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(mes);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(UpdateCheck.class.getResourceAsStream("/icon.png")));
            if (type == Alert.AlertType.CONFIRMATION) {
                Optional<ButtonType> result = alert.showAndWait();
                if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                    try {
                        Desktop.getDesktop().browse(URI.create("https://starkwood.jimdofree.com/hilfe/downloader?ref=dl"));
                        System.exit(-500);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            } else {
                alert.showAndWait();
            }
        });
    }
}
