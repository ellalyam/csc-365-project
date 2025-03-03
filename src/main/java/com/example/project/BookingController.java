package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {





    }


    public void setTripDetails(String fromCity, String toCity, String date, String time, String price){ //for booking page
        bookFrom.setText(fromCity);
        bookTo.setText(toCity);
        bookingDate.setText(date);
        bookTime.setText(time);
        bookPrice.setText(price);

    }

}
