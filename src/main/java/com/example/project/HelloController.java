package com.example.project;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label showuserlabel;
    @FXML
    private TextField fromField;

    @FXML
    private TextField toField;

    @FXML
    private DatePicker dateField;

    @FXML
    private Button searchButton;
    @FXML
    private TextField numberPassengersField;


    public void connectButton(ActionEvent event){
        SQLConnection connectNow = new SQLConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "SELECT name from Passengers;";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(connectQuery);

            while (rs.next()){
                showuserlabel.setText(rs.getString(1));


            }

        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String fromCity = fromField.getText().trim();
                String toCity = toField.getText().trim();
                LocalDate selectedDate = dateField.getValue();
                String numPassStr = numberPassengersField.getText().trim();
                int numPass = Integer.parseInt(numPassStr);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String date = selectedDate.format(formatter);

                // Validate inputs before proceeding
                if (fromCity.isEmpty() || toCity.isEmpty() || date.isEmpty() || numPassStr.isEmpty()) {
                    System.out.println("All fields must be filled out!");
                    return;
                }

                DBUtils.searchTrip(actionEvent, "/com/example/project/available-trips.fxml", "Available Trips", fromCity, toCity, date, numPass);
            }
        });

    }





}