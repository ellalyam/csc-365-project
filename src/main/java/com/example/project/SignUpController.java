package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import java.sql.Connection;

public class SignUpController implements Initializable {
    private String previousPage;
    @FXML
    private TextField nameSignUp;
    @FXML
    private TextField emailSignUp;
    @FXML
    private Button createAccountButton;
    private String toCity;
    private String fromCity;
    private String date;
    private String time;
    private String price;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        createAccountButton.setOnMouseClicked(event -> {
            System.out.println("sign up created");
            String name = nameSignUp.getText().trim();
            String email = emailSignUp.getText().trim();

            if(name.isEmpty() || email.isEmpty()) {
                System.out.println("Please enter both name and email");
                return;
            }

            insertUser(name, email);

            DBUtils.proceedToBooking(event, previousPage, "Confirm Booking", fromCity, toCity, date, time, price);
        });
    }

    public static void insertUser(String name, String email) {
        String query = "INSERT INTO Passengers(name, email) VALUES (?, ?)";

        try(Connection connect = SQLConnection.getConnection()) {
            PreparedStatement stmt = connect.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);

            int rowsInserted = stmt.executeUpdate();

            if(rowsInserted <= 0) {
                System.out.println("Failed to insert");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPreviousPage(String page, String toCity, String fromCity, String date, String time, String price) {
        this.previousPage = page;
        this.toCity = toCity;
        this.fromCity = fromCity;
        this.date = date;
        this.time = time;
        this.price = price;
    }

}
