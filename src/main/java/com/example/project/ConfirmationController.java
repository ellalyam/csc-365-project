package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationController implements Initializable {

    @FXML
    private Label passengerName;
    @FXML
    private Button bookAnotherTrip;
    @FXML
    private Label rid;

    public void initialize(URL location, ResourceBundle resources){}

    public void confirmationMessage(String name){ //include price later
        passengerName.setText(name);

    }

    public void bookAnother(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("/com/example/project/search-trip.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Find Trips!");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the back scene.");
        }

    }


}
