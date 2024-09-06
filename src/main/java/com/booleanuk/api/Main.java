package com.booleanuk.api;

import com.booleanuk.api.config.DBConnection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try {
            DBConnection connection = new DBConnection();
            System.out.println("Connected!");
            System.out.println(connection);
        }
        catch (SQLException sqlException) {
            System.out.println("Failed to connect: " + sqlException.getMessage());
        }

    }
}
