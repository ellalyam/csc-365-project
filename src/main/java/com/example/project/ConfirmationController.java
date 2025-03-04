package com.example.project;

import javafx.fxml.Initializable;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationController implements Initializable {

    @FXML
    private Label passengerName;

    public void initialize(URL location, ResourceBundle resources){}

    public void confirmationMessage(String name){ //include price later
        passengerName.setText(name);

    }


}
