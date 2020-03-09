package de.Jo0001.StarkWood.Downloader;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getInfo() throws IOException {
        System.out.println("Checking for updates...");
        final URL myurl = new URL("https://www.dropbox.com/s/h3kvtvzy9i5kmr4/infos.starkwood?dl=1");
        HttpURLConnection con = (HttpURLConnection) myurl.openConnection();
        con.setDoOutput(true);
        String[] infos = new String[8];
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            for (int i = 0; i < infos.length; i++) {
                infos[i] = in.readLine();
            }
            con.disconnect();
            final String cVer = "1.3";
            if (cVer.equalsIgnoreCase(infos[7])) {
                System.out.println(cVer + " is already the newest version");
            } else {
                System.err.println("Outdated version, please download the new " + infos[7] + " version");
                localAlert("StarkWood - Veraltete Version", "Veraltete Version, klicke auf OK um die Downloadseite zu öffnen", Alert.AlertType.CONFIRMATION);
            }
        } catch (UnknownHostException | SocketTimeoutException e) {
            System.err.println("No network connection!");
            e.printStackTrace();
            localAlert("StarkWood - Keine Netzwerkverbindung", "Keine Netzwerkverbindung! Bitte überprüfe deine Verbindung", Alert.AlertType.ERROR);
        }

    }

    private void localAlert(String title, String mes, Alert.AlertType type) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            if (type == Alert.AlertType.CONFIRMATION) {
                alert.setGraphic(null);
            }
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(mes);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
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
