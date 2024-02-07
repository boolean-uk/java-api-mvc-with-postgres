package com.booleanuk.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

//@SpringBootApplication
public class Main {
    public static void main(String[] args){
        try {
            EmployeeRepository employees = new EmployeeRepository();

            for (Employee employee : employees.getAll()){
                System.out.println(employee);
            }

            System.out.println(employees.getOne(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
