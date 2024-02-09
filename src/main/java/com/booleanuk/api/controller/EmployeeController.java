package com.booleanuk.api.controller;

import com.booleanuk.api.model.Employee;
import com.booleanuk.api.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    public EmployeeController() throws SQLException {
        this.employeeRepository = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.employeeRepository.getAll();
    }

    @GetMapping("/{id}")
    public Employee get(@PathVariable int id) throws SQLException {
        return this.employeeRepository.get(id);
    }

    @PostMapping
    public Employee add(@RequestBody Employee employee) throws SQLException {
        return this.employeeRepository.add(employee);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable int id, @RequestBody Employee employee) throws SQLException {
        return this.employeeRepository.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable int id) throws SQLException {
        return this.employeeRepository.delete(id);
    }
}
