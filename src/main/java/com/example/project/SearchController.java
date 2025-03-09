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
import java.util.ResourceBundle;


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
    private Button searchLogin;





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
            String help = (String) fromCityDropdown.getValue();  // Get the selected city
            if (help != null) {
                loadToCity(help);  // Load toCity based on the selected fromCity
            } else {
                System.out.println("No city selected");
            }
        });

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String fromCity = (String) fromCityDropdown.getValue();
                String toCity = (String) toCityDropdown.getValue();
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

        // ONLY PRESS AFTER LOGGED IN AT BOOKING PAGE!!
        searchViewTrips.setOnAction(event -> {
            try {
                if (UserLogin.getName() != null && UserLogin.getEmail() != null) { // if user already logged in, show trips
                    DBUtils.viewYourTrips(event, "/com/example/project/your-trips.fxml", "Your Trips", UserLogin.getName(), UserLogin.getEmail());
                } else { // if not logged in, go to log in page
                    FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("/com/example/project/log-in.fxml"));
                    Parent root = loader.load(); // This line may throw IOException
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Log In");
                    stage.setScene(new Scene(root, 600, 400));
                    stage.show();
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to load the scene.");
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
        String query1 = "SELECT DISTINCT toCity FROM Departures WHERE fromCity = ?"; // Correct query

        try {
            // Use PreparedStatement for parameterized queries
            PreparedStatement preparedStatement = connectDB.prepareStatement(query1);

            // Set the value of the fromCity parameter
            preparedStatement.setString(1, city);  // The '1' represents the first ? in the query

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();  // Executes the query with the city value

            // Process the result set
            while (rs.next()) {
                items1.add(rs.getString("toCity"));
            }
        } catch (Exception e) {
        e.printStackTrace();
    }

        // Set the items to the ComboBox
        toCityDropdown.setItems(items1);

    }





}