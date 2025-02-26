package com.example.project;

import java.sql.*;

public class SQLConnection {
    static   Connection connect;
    public Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu/anavos", "anavos", "029760100");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connect;

    }





}

