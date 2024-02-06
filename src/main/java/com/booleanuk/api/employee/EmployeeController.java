package com.booleanuk.api.employee;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository employees;

    public EmployeeController() throws SQLException {
        this.employees = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.employees.getAll();
    }

    @PostMapping
    public Employee create(@RequestBody Employee employee) throws SQLException {
        return this.employees.add(employee);
    }

    @GetMapping("/{id}")
    public Employee get(@PathVariable(name = "id") int id) throws SQLException {
        return this.employees.get(id);
    }
}
