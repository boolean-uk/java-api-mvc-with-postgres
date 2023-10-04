package com.booleanuk.api.employee;

import com.booleanuk.api.dbConfiguration.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        EmployeeRepository myRepo = null;
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            myRepo = new EmployeeRepository(databaseConnection);
        }
        catch(Exception e) {
            System.out.println("Oops: " + e);
        }
        try {
            for (Employee employee : myRepo.getAll())
                System.out.println(employee);
        }
        catch(Exception e) {
            System.out.println("Oops: " + e);
        }
    }
}