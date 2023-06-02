package com.booleanuk.api;

import com.booleanuk.api.model.Employee;
import com.booleanuk.api.repository.EmployeeRepository;

public class Main {
    public static void main(String[] args) {
        EmployeeRepository myRepo = null;

        try{
            myRepo = new EmployeeRepository();
        } catch(Exception e) {
            System.out.println("Uh Oh: " + e);
        }

        try {
            Employee employee = new Employee(0, "Aidan", " Junior Fullstack Developer", "C", "Software");
            System.out.println(myRepo.update(4, employee));
        } catch (Exception e) {
            System.out.println("Oh dear: " + e);
        }

    }
}
