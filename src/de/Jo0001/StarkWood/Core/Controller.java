package de.Jo0001.StarkWood.Core;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Button btn;


    public Controller() {
        System.out.println("Controller loading");
    }

    public void onBtnPress(ActionEvent buttonEvent) {
        btn.setText("Pressed: " + btn.getText());

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

}
