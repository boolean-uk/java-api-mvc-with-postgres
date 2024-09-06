package com.booleanuk.api.controller;

import com.booleanuk.api.model.Employee;
import com.booleanuk.api.model.EmployeeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository repository;

    public EmployeeController() {
        this.repository = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        List<Employee> allEmployees = new ArrayList<>();
        try {
            allEmployees = this.repository.getAll();
            return allEmployees;
        }
        catch (SQLException sqlException) {
            System.out.println("Failed to get employees from db: " + sqlException);
        }
        return allEmployees;
    }

    @GetMapping("{id}")
    public Employee getOneEmployee(@PathVariable int id) {
        return this.repository.getOneEmployee(id);
    }
}
