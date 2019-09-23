package de.Jo0001.StarkWood.Core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class Main extends Application {
    private static HttpURLConnection con;
    static final FXMLLoader loader = new FXMLLoader();

    LoadingController lControl = new LoadingController();

    @Override
    public void start(Stage primaryStage) throws Exception {
       /* Controller controller = loader.getController();
        loader.setController(controller);*/
       LoadingController loadingController =loader.getController();
       loader.setController(loadingController);

        Parent root = loader.load(getClass().getResource("/fxml/loadScreen.fxml"));
        primaryStage.setTitle("StarkWood");
        primaryStage.setScene(new Scene(root, 310, 235));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/fxml/style.css").toExternalForm());
        primaryStage.show();



        getInfo();
    }

    public void getInfo() throws IOException {
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

    public void updateCheck(String ver){
        final String cVer = "1.1";
        if (cVer.equalsIgnoreCase(ver)) {
            System.out.println("Aktuell");
        } else {
            System.err.println("Veraltete Version, bitte downloade die neuste Version von LINK");
            System.exit(400);
        }


    }

    public void alert(String text){

        Alert alert = new Alert(Alert.AlertType.WARNING, text);
        alert.setHeaderText(null);
        alert.setResizable(false);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        Application.launch(Main.class);
    }
}
