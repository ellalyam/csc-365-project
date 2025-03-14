package com.example.project;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
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

    @FXML
    private TableColumn<ObservableList<String>, String> priceColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> durationColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> RIDColumn;
    @FXML
    private Button previousAvailable;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fromCityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        toCityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        seatsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));
        durationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(6)));

        tripsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ObservableList<String> selectedTrip = tripsTable.getSelectionModel().getSelectedItem();
                if (selectedTrip != null) {
                    DBUtils.proceedToBooking(event, "/com/example/project/booking.fxml", "Confirm Booking",
                            selectedTrip.get(0), // fromCity
                            selectedTrip.get(1), // toCity
                            selectedTrip.get(2), // date
                            selectedTrip.get(3), // time
                            selectedTrip.get(5)); // price, fix this, currently showing seats
                }
            }
        });



    }

    public void loadTrips(String fromCity, String toCity, String date) {
        ObservableList<ObservableList<String>> tripsData = FXCollections.observableArrayList();


        String query = "SELECT fromCity, toCity, date, time, price, duration, (capacity - seatsTaken) AS seatsAvailable " +
                "FROM Departures " +
                "WHERE fromCity = ? AND toCity = ? AND date = ? AND (capacity - seatsTaken) >= 1";

        try (Connection conn = SQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, fromCity);
            stmt.setString(2, toCity);
            stmt.setString(3, date);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ObservableList<String> trip = FXCollections.observableArrayList();
                    trip.add(rs.getString("fromCity"));
                    trip.add(rs.getString("toCity"));
                    trip.add(rs.getString("date"));
                    trip.add(rs.getString("time"));
                    trip.add(rs.getString("seatsAvailable"));
                    trip.add(rs.getString("price"));
                    trip.add(rs.getString("duration"));
                    tripsData.add(trip);
                }
            }

            tripsTable.setItems(tripsData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showTripInfo(String fromCity, String toCity, String date){
        fromcitydetails.setText(fromCity);
        tocitydetails.setText(toCity);
        tripdatedetails.setText(date);

    }


    public void backButtonSearch(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("/com/example/project/search-trip.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Find Trips!");
            stage.setScene(new Scene(root, 1180, 820));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the back scene.");
        }

    }




}
