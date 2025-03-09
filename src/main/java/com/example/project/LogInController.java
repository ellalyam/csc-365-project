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
                DBUtils.viewYourTrips(event, "/com/example/project/your-trips.fxml", "Your Trips", name, email);
            } else {
                System.out.println("Create an account");
            }
        });




    }




}
