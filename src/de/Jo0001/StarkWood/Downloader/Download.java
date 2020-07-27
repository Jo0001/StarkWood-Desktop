package de.Jo0001.StarkWood.Downloader;

import de.Jo0001.StarkWood.Core.Controller;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Download extends Thread {
    private Controller controller;
    private int type;

    public Download(int t, Controller controller) {
        this.type = t;
        this.controller = controller;
    }

    public void run() {
        try {
            startDownload(type);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param type Which type should be downloaded
     * @throws IOException Thrown when something with the url went wrong
     */
    private void startDownload(int type) throws IOException, ParseException {
        if (Store.hash1 == null) {
            UpdateCheck.getInfo();
        }
        String hash = null, link = null;
        if (type == 0) {
            hash = Store.hash1;
            link = "https://www.dropbox.com/s/43qat7lqff29uf9/vanilla.zip?dl=1";
        } else if (type == 1) {
            hash = Store.hash2;
            link = "https://www.dropbox.com/s/y034ryp3ucw1p8q/lite.zip?dl=1";
        } else if (type == 2) {
            hash = Store.hash3;
            link = "https://www.dropbox.com/s/tftwxa1190zcdd6/full%2B%2B.zip?dl=1";
        }
        final URL dl_url = new URL(link);

        URLConnection urlConnection = dl_url.openConnection();//TODO Surround with try catch
        urlConnection.connect();
        double file_size = (double) urlConnection.getContentLength() / 1024 / 1000;
        System.out.println("Starting download of " + type + " (" + file_size + "MB) with hash " + hash);

        ReadableByteChannel rbc = Channels.newChannel(dl_url.openStream());
        FileOutputStream fos = new FileOutputStream(System.getenv("APPDATA") + "\\.minecraft\\server-resource-packs\\" + hash);
        fos.getChannel().transferFrom(rbc, 0, Integer.MAX_VALUE);
        fos.close();
        rbc.close();

        alert("StarkWood - Download fertig", "Download fertig :)", Alert.AlertType.INFORMATION);
        System.out.println("Done");
        controller.reset();
    }

    private void alert(String title, String mes, Alert.AlertType alertType) {
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
