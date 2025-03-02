package com.example.project;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUtils {

    public static void searchTrip(ActionEvent event, String fxmlFile, String title, String fromCity, String toCity, String date){
        Parent root = null;

        if (fromCity != null && toCity != null && date != null){
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                HelloController helloController = loader.getController();
                helloController.showTripInfo(fromCity, toCity, date);


            }catch (IOException e) {
                e.printStackTrace();
            }
        }



        if (root != null) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } else {
            System.out.println("Failed to load FXML file.");
        }



    }





}
