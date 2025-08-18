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

    public Employee getEmployeeById(int id) {
        return repository.getEmployeeById(id);
    }

    public void addEmployee(Employee employee) {
        repository.addEmployee(employee);
    }

    public void updateEmployee(int id, Employee employee) {
        repository.updateEmployee(id, employee);
    }

    public void deleteEmployee(int id) {
        repository.deleteEmployee(id);
    }
}
