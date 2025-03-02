package com.example.project;

import java.sql.*;

public class SQLConnection {
    static   Connection connect;
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu/travel", "travel", "ave");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connect;

    }





}

