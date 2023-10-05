package com.booleanuk.api.Employee;

import com.booleanuk.api.Employee.EmployeeRepository;

public class Main {
    public static void main(String[] args) {
        try {
            EmployeeRepository myRepo = new EmployeeRepository();
            System.out.println(myRepo.getAll());
        } catch (Exception e) {
            System.out.println("Main error: " + e);
        }
    }
}