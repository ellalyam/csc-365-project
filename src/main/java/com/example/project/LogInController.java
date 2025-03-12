package com.example.project;

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
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    @FXML
    private TextField loginName;
    @FXML
    private TextField loginEmail;
    @FXML
    private Button loginButton;
    @FXML
    private Button loginSignUp;

    @Override
    public void initialize(URL location, ResourceBundle resources){

        loginButton.setOnAction(event -> {
            String name = loginName.getText().trim();
            String email = loginEmail.getText().trim();


            if(name.isEmpty() || email.isEmpty()) {
                System.out.println("Please enter both your name and email");
            }


            if(BookingController.userExists(name, email)) {
                UserLogin.setName(name);
                UserLogin.setEmail(email);
                System.out.println("Name:" + UserLogin.getName());
                System.out.println("Email:" + UserLogin.getEmail());

                try {
                    FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("/com/example/project/search-trip.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Find Trips!");
                    stage.setScene(new Scene(root, 1180, 820));
                    stage.show();


                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Failed to load FXML file.");
                }


            } else {
                System.out.println("Create an account");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Account does not exist.");
                alert.show();
            }
        });

        loginSignUp.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project/sign-up.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setTitle("Sign Up");
                stage.setScene(new Scene(root, 1180, 820));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }


        });





    }




}
