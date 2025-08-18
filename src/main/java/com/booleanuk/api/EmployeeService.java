package com.booleanuk.api;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAllEmployees() {
        return repository.getAllEmployees();
    }

    public void addEmployee(Employee employee) {
        repository.addEmployee(employee);
    }
}
