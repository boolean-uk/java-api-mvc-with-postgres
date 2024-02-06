package com.booleanuk.api;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        try {
            EmployeeRepository employeeRepo = new EmployeeRepository();
            for (Employee employee : employeeRepo.getAll()){
                System.out.println(employee);
            }
        } catch (SQLException e) {
            System.out.println("Ooops: " + e);
        }
    }
}
