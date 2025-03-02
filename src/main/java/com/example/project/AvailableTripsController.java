package com.example.project;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AvailableTripsController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fromCityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        toCityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        seatsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));

<<<<<<< HEAD
=======
        loadTrips();
    }
>>>>>>> 11437ad636ba4820069bdac97763e04a102ced09

    public void loadTrips(String fromCity, String toCity, String date, int numberOfPeople) {
        ObservableList<ObservableList<String>> tripsData = FXCollections.observableArrayList();

<<<<<<< HEAD
=======
        String query = "SELECT fromCity, toCity, date, time, (busCapacity - seatsTaken) AS seatsAvailable " +
                "FROM Departures " +
                "WHERE fromCity = ? AND toCity = ? AND date = ? AND (busCapacity - seatsTaken) >= ?";

        try (Connection conn = SQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set user input values into the query
            stmt.setString(1, fromCity);
            stmt.setString(2, toCity);
            stmt.setString(3, date);
            stmt.setInt(4, numberOfPeople);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ObservableList<String> trip = FXCollections.observableArrayList();
                    trip.add(rs.getString("fromCity"));
                    trip.add(rs.getString("toCity"));
                    trip.add(rs.getString("date"));
                    trip.add(rs.getString("time"));
                    trip.add(rs.getString("seatsAvailable"));
                    tripsData.add(trip);
                }
            }

            tripsTable.setItems(tripsData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
>>>>>>> 11437ad636ba4820069bdac97763e04a102ced09


    public void showTripInfo(String fromCity, String toCity, String date){
        fromcitydetails.setText(fromCity);
        tocitydetails.setText(toCity);
        tripdatedetails.setText(date);

    }


}
