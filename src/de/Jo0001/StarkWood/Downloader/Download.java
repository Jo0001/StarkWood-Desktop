package de.Jo0001.StarkWood.Downloader;

import de.Jo0001.StarkWood.Core.Controller;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Download extends Thread {
    private HttpURLConnection con;
    private Controller controller;
    int type;

    public Download(int t, Controller controller) {
        this.type = t;
        this.controller = controller;
    }

    public void run() {
        try {
            startDownload(type);
        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param type Which type should be downloaded
     * @throws IOException Thrown when something with the url went wrong
     */
    public void startDownload(int type) throws IOException, AWTException {
        System.out.println("Fetching info...");
        //Main.sendTray("Download wurde gestartet");
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
            alert("StarkWood - Error ","Keine Netzwerkverbindung!", Alert.AlertType.ERROR);
            controller.reset();
            e.printStackTrace();
        }

        /*Infos[0] vanilla, [1]lite, [2]full, [7]date, [8] version*/

        String hash = null, link = null, mb = null;
        if (type == 0) {
            hash = infos[0];
            link = "https://www.dropbox.com/sh/x5moafif0w3mnfe/AACHeceFYOf_KcmwoOsfwUeWa?dl=1";
            mb = infos[3];

        } else if (type == 1) {
            hash = infos[1];
            link = "https://www.dropbox.com/sh/8kdowv83vjar9qp/AABTJgGfb6ojW-Xi3H8300m6a?dl=1";
            mb = infos[4];

        } else if (type == 2) {
            hash = infos[2];
            link = "https://www.dropbox.com/sh/q7r6vehzc3j1xrw/AADf9zH24QUF8yKFpww4WTbca?dl=1";
            mb = infos[5];
        }
        final URL dl_url = new URL(link);

        System.out.println("Starting download of " + type + " (" + mb + "MB) with hash " + hash);
        //TODO network error handling
        ReadableByteChannel rbc = Channels.newChannel(dl_url.openStream());
        FileOutputStream fos = new FileOutputStream(System.getenv("APPDATA") + "\\.minecraft\\server-resource-packs\\" + hash);
        fos.getChannel().transferFrom(rbc, 0, Integer.MAX_VALUE);
        fos.close();
        rbc.close();

        alert("StarkWood - Download fertig","Download fertig :)", Alert.AlertType.INFORMATION);
        System.out.println("Done");
        controller.reset();
    }

    private void alert(String title,String mes, Alert.AlertType alertType) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(mes);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
            alert.showAndWait();
        });
    }
}
