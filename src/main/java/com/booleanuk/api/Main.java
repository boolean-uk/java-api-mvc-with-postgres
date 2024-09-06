package com.booleanuk.api;
import com.booleanuk.api.model.Employee;
import com.booleanuk.api.model.EmployeeRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        EmployeeRepository repository = new EmployeeRepository();

        for (Employee employee : repository.getAll()) {
            System.out.println(employee);
        }

    }
}
