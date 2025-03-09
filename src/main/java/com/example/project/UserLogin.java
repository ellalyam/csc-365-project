package com.example.project;

public class UserLogin {

    private static String name;
    private static String email;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserLogin.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserLogin.email = email;
    }
}
