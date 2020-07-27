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
    Button btn;
    @FXML
    ChoiceBox slc;

    public Controller() {
        System.out.println("Controller loading");
    }

    private void onBtnPress(ActionEvent buttonEvent) {
        btn.setText("Downloading...");
        btn.setDisable(true);
        slc.setDisable(true);
        int type =slc.getSelectionModel().getSelectedIndex();
        if(type !=3) {
            Download download = new Download(type, this);
            download.start();
        }else {
            for (int i = 0; i < 3; i++) {
                Download download = new Download(i, this);
                download.start();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //@FXML-Fields are initialized
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                onBtnPress(actionEvent);
            }
        });
    }

    public void reset() {
        Platform.runLater(() -> {
            btn.setText("Herunterladen");
            slc.setDisable(false);
            btn.setDisable(false);
        });
    }
}