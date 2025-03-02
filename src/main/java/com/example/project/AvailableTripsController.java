package com.example.project;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AvailableTripsController {

    @FXML
    private Label fromcitydetails;

    @FXML
    private Label tocitydetails;

    @FXML
    private Label tripdatedetails;






    public void showTripInfo(String fromCity, String toCity, String date){
        fromcitydetails.setText(fromCity);
        tocitydetails.setText(toCity);
        tripdatedetails.setText(date);

    }


}
