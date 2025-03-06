package com.example.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

    //proceed to thank you page
    @FXML
    private Label bookTime;

    @FXML
    private Label bookTo;

    @FXML
    private Label bookFrom;
    @FXML
    private Label bookPrice;
    @FXML
    private Label bookingDate;
    @FXML
    private Button bookingButton;
    @FXML
    private TextField fillName;
    @FXML
    private TextField fillEmail;
    @FXML
    private Button previousBooking;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        bookingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = fillName.getText().trim();
                String email = fillEmail.getText().trim();

                // Validate inputs before proceeding
                if (name.isEmpty() || email.isEmpty()) {
                    System.out.println("All fields must be filled out!");
                    return;
                }

                DBUtils.proceedToConfirmation(actionEvent, "/com/example/project/confirmation.fxml", "Thank You!", name); //include email for insert statement
            }
        });

    }


    public void setTripDetails(String fromCity, String toCity, String date, String time, String price){ //for booking page
        bookFrom.setText(fromCity);
        bookTo.setText(toCity);
        bookingDate.setText(date);
        bookTime.setText(time);
        bookPrice.setText(price);

    }


    public void backButtonAvailable(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("/com/example/project/available-trips.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Available Trips");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();



        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the back scene.");
        }

    }


    private void insertReservation(String name, String email) {
        String query = "INSERT INTO Reservations (name, email, fromCity, toCity, date, time, price) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, bookFrom.getText());
            stmt.setString(4, bookTo.getText());
            stmt.setString(5, bookingDate.getText());
            stmt.setString(6, bookTime.getText());
            stmt.setString(7, bookPrice.getText());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
