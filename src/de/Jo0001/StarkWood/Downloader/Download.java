package de.Jo0001.StarkWood.Downloader;

import de.Jo0001.StarkWood.Core.Controller;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param type Which type should be downloaded
     * @throws IOException Thrown when something with the url went wrong
     */
    private void startDownload(int type) throws IOException {

        if(Store.hash1 == null){
            System.out.println("Fetching info...");
            final URL myurl = new URL("https://www.dropbox.com/s/h3kvtvzy9i5kmr4/infos.starkwood?dl=1");
            HttpURLConnection con = (HttpURLConnection) myurl.openConnection();
            con.setDoOutput(true);
            String[] infos = new String[8];
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                for (int i = 0; i < infos.length; i++) {
                    infos[i] = in.readLine();
                }
                con.disconnect();
                Store.hash1 = infos[0];
                Store.hash2 = infos[1];
                Store.hash3 = infos[2];
            } catch (UnknownHostException | SocketTimeoutException e) {
                System.err.println("No network connection!");
                alert("StarkWood - Error ", "Keine Netzwerkverbindung!", Alert.AlertType.ERROR);
                controller.reset();
                e.printStackTrace();
            }
        }
        /*Infos[0] vanilla, [1]lite, [2]full, [7] version*/

        String hash = null, link = null;
        if (type == 0) {
            hash =Store.hash1;
            link = "https://www.dropbox.com/s/43qat7lqff29uf9/vanilla.zip?dl=1";
        } else if (type == 1) {
            hash =Store.hash2;
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
