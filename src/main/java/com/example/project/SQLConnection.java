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




    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu/anavos", "anavos", "029760100"); // Replace with database name (username), username, and password

            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM STUDENTS;");
            while (rs.next()) {
                String studentName = rs.getString(1); // name is first field
                System.out.println("Student name = " +
                        studentName);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }




}

