package com.example.project;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label showuserlabel;
    @FXML
    private Label fromcitydetails;
    @FXML
    private Label tocitydetails;
    @FXML
    private Label tripdatedetails;
    @FXML
    private Button searchButton;

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
                DBUtils.searchTrip(actionEvent, "com/example/project/available-trips.fxml", "Available Trips", null, null, null);
            }
        });

    }

    public void showTripInfo(String fromCity, String toCity, String date){
        fromcitydetails.setText(fromCity);
        tocitydetails.setText(toCity);
        tripdatedetails.setText(date);

    }



}