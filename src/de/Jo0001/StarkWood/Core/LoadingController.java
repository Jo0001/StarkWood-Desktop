package de.Jo0001.StarkWood.Core;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {
    @FXML
    ProgressBar pgr;


    public LoadingController() {
        System.out.println("Init loading screen");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //@FXML-Fields are initialized
        pgr.setProgress(0.1);
        changeProgress(0.2);


    }

    public void changeProgress(double value) {
        pgr.setProgress(value);



    }

}
