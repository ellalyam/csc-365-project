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
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class yourTripController implements Initializable {
    @FXML
    private Label yourName;
    @FXML
    private Label yourEmail;

    @FXML
    private TableView<ObservableList<String>> yourTripsTable;
    @FXML
    private TableColumn<ObservableList<String>, String> yourFrom;
    @FXML
    private TableColumn<ObservableList<String>, String> yourTo;
    @FXML
    private TableColumn<ObservableList<String>, String> yourDate;
    @FXML
    private TableColumn<ObservableList<String>, String> yourTime;
    @FXML
    private TableColumn<ObservableList<String>, String> yourRID;
    @FXML
    private TableColumn<ObservableList<String>, String> yourDuration;
    @FXML
    private TableColumn<ObservableList<String>, String> yourReservationDate;
    @FXML
    private Button bookYour;
    @FXML
    private Button upcomingTrips;
    @FXML
    private Button previousTrips;
    @FXML
    private Button allTrips;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        upcomingTrips.setOnAction(event -> {
            loadFutureTrips(UserLogin.getName(),UserLogin.getEmail());

        });
        previousTrips.setOnAction(event -> {
            loadPastTrips(UserLogin.getName(),UserLogin.getEmail());

        });
        allTrips.setOnAction(event -> {
            loadYourTrips(UserLogin.getName(),UserLogin.getEmail());

        });
    }

    public void loadYourTrips(String name, String email) {
        ObservableList<ObservableList<String>> tripsData = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM PersonalView " +
                "WHERE name = ? AND email = ?";

        try (Connection conn = SQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set user input values into the query
            stmt.setString(1, name);
            stmt.setString(2, email);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ObservableList<String> trip = FXCollections.observableArrayList();
                    trip.add(rs.getString("rid"));
                    trip.add(rs.getString("date"));
                    trip.add(rs.getString("time"));
                    trip.add(rs.getString("fromCity"));
                    trip.add(rs.getString("toCity"));
                    trip.add(rs.getString("duration"));
                    trip.add(rs.getString("reserveDate"));
                    tripsData.add(trip);
                }
            }

            yourRID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
            yourDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
            yourTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
            yourFrom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
            yourTo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
            yourDuration.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));
            yourReservationDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(6)));


            yourTripsTable.setItems(tripsData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFutureTrips(String name, String email) {
        ObservableList<ObservableList<String>> tripsData = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM PersonalView " +
                "WHERE name = ? AND email = ?" +
                "AND date >= CURDATE()";

        try (Connection conn = SQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, email);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ObservableList<String> trip = FXCollections.observableArrayList();
                    trip.add(rs.getString("rid"));
                    trip.add(rs.getString("date"));
                    trip.add(rs.getString("time"));
                    trip.add(rs.getString("fromCity"));
                    trip.add(rs.getString("toCity"));
                    trip.add(rs.getString("duration"));
                    trip.add(rs.getString("reserveDate"));
                    tripsData.add(trip);
                }
            }

            yourRID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
            yourDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
            yourTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
            yourFrom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
            yourTo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
            yourDuration.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));
            yourReservationDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(6)));


            yourTripsTable.setItems(tripsData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPastTrips(String name, String email) {
        ObservableList<ObservableList<String>> tripsData = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM PersonalView " +
                "WHERE name = ? AND email = ?" +
                "AND date < CURDATE()";

        try (Connection conn = SQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, email);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ObservableList<String> trip = FXCollections.observableArrayList();
                    trip.add(rs.getString("rid"));
                    trip.add(rs.getString("date"));
                    trip.add(rs.getString("time"));
                    trip.add(rs.getString("fromCity"));
                    trip.add(rs.getString("toCity"));
                    trip.add(rs.getString("duration"));
                    trip.add(rs.getString("reserveDate"));
                    tripsData.add(trip);
                }
            }

            yourRID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
            yourDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
            yourTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
            yourFrom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
            yourTo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
            yourDuration.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));
            yourReservationDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(6)));


            yourTripsTable.setItems(tripsData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void showPassengerInfo(String name){
        yourName.setText(name);

    }

    public void bookButtonYour(ActionEvent event) {
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
