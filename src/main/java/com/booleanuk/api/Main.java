package com.booleanuk.api;

import com.booleanuk.api.employees.Employee;
import com.booleanuk.api.employees.EmployeeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws SQLException {
        EmployeeRepository empRepo = new EmployeeRepository();
        try {
            System.out.println(empRepo.getOne(2));
            System.out.println(empRepo.add(
                    new Employee(363,
                            "Beter Gifin",
                            "Biscuit Maker",
                            "6",
                            "Food Production")
            ));
        } catch (Exception e)    {
            System.out.println("Aur naur, " + e);
        }
        //SpringApplication.run(Main.class, args);
    }
}
