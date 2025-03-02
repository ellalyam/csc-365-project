package com.example.project;


import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

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

    @FXML
    private TableView<ObservableList<String>> tripsTable;
    @FXML
    private TableColumn<ObservableList<String>, String> fromCityColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> toCityColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> dateColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> timeColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> seatsColumn;







    public void showTripInfo(String fromCity, String toCity, String date){
        fromcitydetails.setText(fromCity);
        tocitydetails.setText(toCity);
        tripdatedetails.setText(date);

    }


}
