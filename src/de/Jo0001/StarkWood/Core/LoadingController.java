package de.Jo0001.StarkWood.Core;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {
    @FXML
    ProgressBar pgr;

    private static HttpURLConnection con;

    public LoadingController() {
        System.out.println("Init loading screen");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //@FXML-Fields are initialized
        pgr.setProgress(0.1);
        try {
            Main.getInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void changeProgress(double value) {
        pgr.setProgress(value);
        System.out.println(value);
    }

    public void getInfo() throws IOException {

        System.out.println("Laden . . .");
        final URL myurl = new URL("https://www.dropbox.com/s/h3kvtvzy9i5kmr4/infos.starkwood?dl=1");
        con = (HttpURLConnection) myurl.openConnection();
        changeProgress(0.4);
        con.setDoOutput(true);
        String[] infos = new String[8];
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            for (int i = 0; i < infos.length; i++) {
                infos[i] = in.readLine();
            }
            con.disconnect();
        } catch (UnknownHostException e) {
            System.err.println("Keine Netzwerkverbindung!");
            Main.alert("Keine Netzwerkverbindung :(");
            e.printStackTrace();
        }
        changeProgress(1);
        // updateCheck(infos[7]);
    }

}
