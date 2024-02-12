package com.booleanuk.api;


import com.booleanuk.api.employee.Employee;
import com.booleanuk.api.employee.EmployeeRepository;

import java.sql.SQLException;

public class MainDatabase {
    public static void main(String[] args) {
        EmployeeRepository myRepo = null;
        try {
            myRepo = new EmployeeRepository();
        }
        catch(Exception e) {
            System.out.println("Oh no: " + e);
        }

        try {
            Employee employee = new Employee(0, "Alexander", "Fullstack developer", "java", "IT");
            System.out.println(myRepo.update(4, employee));
        } catch (Exception e) {
            System.out.println("Oh no: " + e);
        }
    }
}
