package de.Jo0001.StarkWood.Core;

import de.Jo0001.StarkWood.Downloader.Download;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
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

    public void onBtnPress(ActionEvent buttonEvent) throws IOException {
        btn.setText("Laden . . .");
        Download.startDownload(slc.getSelectionModel().getSelectedIndex());
        btn.setText("Herunterladen");


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //@FXML-Fields are initialized
      btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    onBtnPress(actionEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
