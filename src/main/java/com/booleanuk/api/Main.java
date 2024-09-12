package com.booleanuk.api;

import com.booleanuk.api.employee.Employee;
import com.booleanuk.api.employee.EmployeeRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            EmployeeRepository employeeRepository = new EmployeeRepository();
            System.out.println(employeeRepository.delete(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
