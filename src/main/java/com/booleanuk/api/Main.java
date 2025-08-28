package com.booleanuk.api;


import org.springframework.boot.SpringApplication;

import java.sql.SQLException;

public class Main {
    public static void main(String [] args) throws SQLException {

        EmployeeRepository er = new EmployeeRepository();

        try {
            er.testConnectionToDB();
        } catch (Exception e) {
            System.out.println("Oops: " + e);
        }

    }
}
