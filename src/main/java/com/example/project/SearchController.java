package com.example.project;

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
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;


public class SearchController implements Initializable {
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

    @FXML
    private ComboBox fromCityDropdown;
    @FXML
    private ComboBox toCityDropdown;

    @FXML
    private Button searchViewTrips;
    @FXML
    private Button logoutButton;





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
        loadDropdowns();
        fromCityDropdown.setOnAction(event -> {
            String from = (String) fromCityDropdown.getValue();
            if (from != null) {
                loadToCity(from);
                updateDatePicker();
            } else {
                System.out.println("No city selected");
            }
        });

        toCityDropdown.setOnAction(event -> {
            updateDatePicker();
        });

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String fromCity = (String) fromCityDropdown.getValue();
                String toCity = (String) toCityDropdown.getValue();
                LocalDate selectedDate = dateField.getValue();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String date = selectedDate.format(formatter);

                if (fromCity.isEmpty() || toCity.isEmpty() || date.isEmpty()) {
                    System.out.println("All fields must be filled out!");
                    return;
                }

                DBUtils.searchTrip(actionEvent, "/com/example/project/available-trips.fxml", "Available Trips", fromCity, toCity, date);
            }
        });

        searchViewTrips.setOnAction(event -> {
            try {
                if (UserLogin.getName() != null && UserLogin.getEmail() != null) {
                    DBUtils.viewYourTrips(event, "/com/example/project/your-trips.fxml", "Your Trips", UserLogin.getName(), UserLogin.getEmail());
                } else { // if not logged in, go to log in page
                    FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("/com/example/project/log-in.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Log In");
                    stage.setScene(new Scene(root, 1180, 820));
                    stage.show();
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to load the scene.");
            }
        });

       logoutButton.setOnAction(event -> {
           if (UserLogin.getName() != null && UserLogin.getEmail() != null) {
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("Logout Confirmation");
               alert.setHeaderText("You are about to log out.");
               alert.setContentText("Are you sure you want to log out?");

               Optional<ButtonType> result = alert.showAndWait();

               if (result.isPresent() && result.get() == ButtonType.OK) {
                   UserLogin.setName(null);
                   UserLogin.setEmail(null);
                   System.out.println("User logged out successfully.");

                   try {
                       FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("/com/example/project/log-in.fxml"));
                       Parent root = loader.load();
                       Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                       stage.setTitle("Log In");
                       stage.setScene(new Scene(root, 1180, 820));
                       stage.show();
                   } catch (IOException e) {
                       e.printStackTrace();
                       System.out.println("Failed to load the login scene.");
                   }
               } else {
                   System.out.println("Logout canceled.");
               }
           }
        });





    }

    public void loadDropdowns() {
        SQLConnection connectNow = new SQLConnection();
        Connection connectDB = connectNow.getConnection();

        ObservableList<String> items = FXCollections.observableArrayList();
        String query = "SELECT DISTINCT fromCity FROM Departures";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                items.add(rs.getString("fromCity"));


            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        fromCityDropdown.setItems(items);


    }


    public void loadToCity(String city){
        SQLConnection connectNow = new SQLConnection();
        Connection connectDB = connectNow.getConnection();
        ObservableList<String> items1 = FXCollections.observableArrayList();
        String query1 = "SELECT DISTINCT toCity FROM Departures WHERE fromCity = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(query1);

            preparedStatement.setString(1, city);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                items1.add(rs.getString("toCity"));
            }
        } catch (Exception e) {
        e.printStackTrace();
    }

        toCityDropdown.setItems(items1);

    }


    public Set<LocalDate> getAvailableDates(String fromCity, String toCity) {
        Set<LocalDate> availableDates = new HashSet<>();
        String query = "SELECT DISTINCT date FROM Departures WHERE fromCity = ? AND toCity = ?";

        try (Connection conn = SQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, fromCity);
            stmt.setString(2, toCity);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    availableDates.add(rs.getDate("date").toLocalDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableDates;
    }

    public void highlightAvailableDates(DatePicker datePicker, String fromCity, String toCity) {
        Set<LocalDate> availableDates = getAvailableDates(fromCity, toCity);

        datePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setDisable(true);
                    return;
                }

                if (availableDates.contains(item)) {
                    setStyle("-fx-background-color: #90EE90;");
                } else {
                    setDisable(true);
                }
            }
        });
    }

    private void updateDatePicker() {
        String fromCity = (String) fromCityDropdown.getValue();
        String toCity = (String) toCityDropdown.getValue();

        if (fromCity != null && toCity != null) {
            highlightAvailableDates(dateField, fromCity, toCity);
        }
    }


}