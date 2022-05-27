package com.example.kck;


import java.sql.Connection;
import java.sql.DriverManager;

public class Polaczenie {
    public Connection databaseLink;

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.databaseLink = DriverManager.getConnection("jdbc:mysql://localhost:3306/sklepwhisky", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return this.databaseLink;
    }
}
