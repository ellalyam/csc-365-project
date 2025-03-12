package com.example.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    @FXML
    private Button viewTrips;
    @FXML
    private Label passengerEmail;

    public void initialize(URL location, ResourceBundle resources){
        viewTrips.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = passengerName.getText();
                String email = passengerEmail.getText();

                DBUtils.viewYourTrips(actionEvent, "/com/example/project/your-trips.fxml", "Your Trips", name, email);
            }
        });


    }

    public void confirmationMessage(String name, int reservationId, String email){ //include price later
        passengerName.setText(name);
        rid.setText("" + reservationId);
        passengerEmail.setText(email);

    }

    public void bookAnother(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("/com/example/project/search-trip.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Find Trips!");
            stage.setScene(new Scene(root, 1180, 820));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the back scene.");
        }

    }


}
