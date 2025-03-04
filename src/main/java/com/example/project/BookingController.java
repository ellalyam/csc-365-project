package com.example.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
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

}
