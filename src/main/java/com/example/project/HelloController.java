package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HelloController {
    @FXML
    private Label showuserlabel;


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

}