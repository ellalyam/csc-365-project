package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    @FXML
    private Button signUpBack;

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

            if (BookingController.userExists(name, email)){
                System.out.println("Name and email taken.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sign Up");
                alert.setHeaderText("Failed to Sign Up");
                alert.setContentText("Name and email already taken.");
                alert.show();

            } else{
                insertUser(name, email);

                UserLogin.setName(name);
                UserLogin.setEmail(email);
                System.out.println("Name:" + UserLogin.getName());
                System.out.println("Name:" + UserLogin.getEmail());


                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project/search-trip.fxml"));
                    Parent root = loader.load();

                    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    stage.setTitle("Find Trips!");
                    stage.setScene(new Scene(root, 600, 400));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Failed to load FXML file.");
                }

            }


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

    public void backButtonSignUp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("/com/example/project/log-in.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Log In");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the back scene.");
        }

    }

}
