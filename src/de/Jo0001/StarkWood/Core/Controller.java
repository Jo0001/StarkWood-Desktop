package de.Jo0001.StarkWood.Core;

import de.Jo0001.StarkWood.Downloader.Download;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public Button btn;
    @FXML
    ChoiceBox slc;

    public Controller() {
        System.out.println("Controller loading");
    }

    public void onBtnPress(ActionEvent buttonEvent) throws InterruptedException {
        btn.setText("Downloading...");
        btn.setDisable(true);
        slc.setDisable(true);
        Download download = new Download(slc.getSelectionModel().getSelectedIndex(), this);
        download.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //@FXML-Fields are initialized
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    onBtnPress(actionEvent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void test() {
        Platform.runLater(() -> {
            btn.setText("Herunterladen");
            slc.setDisable(false);
            btn.setDisable(false);
        });
    }
}