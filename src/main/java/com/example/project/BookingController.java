package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BookingController {

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
    private Label bookDate;


    public void setTripDetails(String fromCity, String toCity, String date, String time){ //for booking page
        bookFrom.setText(fromCity);
        bookTo.setText(toCity);
        bookDate.setText(date);
        bookTime.setText(time);

    }

}
