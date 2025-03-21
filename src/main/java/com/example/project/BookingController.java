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
import java.sql.ResultSet;
import java.sql.Statement;
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
    private TextField fillName;
    @FXML
    private TextField fillEmail;
    @FXML
    private Button confirmBooking;

    @FXML
    private Button previousBooking;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        confirmBooking.setOnAction(event -> {

            String name = UserLogin.getName();
            String email = UserLogin.getEmail();

            if(name.isEmpty() || email.isEmpty()) {
                System.out.println("Please enter both your name and email");
            }

            if(userExists(name, email)) {
                String fromCity = bookFrom.getText();
                String toCity = bookTo.getText();
                String date = bookingDate.getText();
                String time = bookTime.getText();

                UserLogin.setName(name);
                UserLogin.setEmail(email);
                System.out.println("Name:" + UserLogin.getName());
                System.out.println("Email:" + UserLogin.getEmail());


                DBUtils.proceedToConfirmation(event, "/com/example/project/confirmation.fxml", "Thank You!", name, email, fromCity, toCity, date, time);
            } else {
                System.out.println("Create an account");
            }
        });

    }


    public void setTripDetails(String fromCity, String toCity, String date, String time, String price){
        bookFrom.setText(fromCity);
        bookTo.setText(toCity);
        bookingDate.setText(date);
        bookTime.setText(time);
        bookPrice.setText(price);

    }

    // Check if user exists
    public static boolean userExists(String name, String email) {
        String query = "SELECT COUNT(*) FROM Passengers WHERE name = ? AND email = ?";

        try(Connection connect = SQLConnection.getConnection()) {
            PreparedStatement stmt = connect.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public void backButtonAvailable(ActionEvent event) {
        /*
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

         */
        DBUtils.searchTrip(event, "/com/example/project/available-trips.fxml", "Available Trips", bookFrom.getText(), bookTo.getText(), bookingDate.getText());


    }

    public static int insertReservation(String name, String email, String fromCity, String toCity, String date, String time) {
        String queryGetDid = "SELECT did FROM Departures WHERE fromCity = ? AND toCity = ? AND date = ? AND time = ?";

        try (Connection conn = SQLConnection.getConnection();
             PreparedStatement stmtGetDid = conn.prepareStatement(queryGetDid)) {

            stmtGetDid.setString(1, fromCity);
            stmtGetDid.setString(2, toCity);
            stmtGetDid.setString(3, date);
            stmtGetDid.setString(4, time);

            try (ResultSet rs = stmtGetDid.executeQuery()) {
                if (rs.next()) {
                    int tripId = rs.getInt("did");

                    String queryInsertReservation = "INSERT INTO Reservations (name, email, did, reserveDate) VALUES (?, ?, ?, ?)";

                    try (PreparedStatement stmtInsert = conn.prepareStatement(queryInsertReservation, Statement.RETURN_GENERATED_KEYS)) {
                        stmtInsert.setString(1, name);
                        stmtInsert.setString(2, email);
                        stmtInsert.setInt(3, tripId);
                        stmtInsert.setDate(4, java.sql.Date.valueOf(LocalDate.now()));

                        stmtInsert.executeUpdate();

                        String updateSeatsQuery = "UPDATE Departures SET seatsTaken = seatsTaken + 1 WHERE did = ?";
                        try (PreparedStatement stmtUpdateSeats = conn.prepareStatement(updateSeatsQuery)) {
                            stmtUpdateSeats.setInt(1, tripId);
                            stmtUpdateSeats.executeUpdate();
                        }


                        try (ResultSet generatedKeys = stmtInsert.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                return generatedKeys.getInt(1);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }





}
