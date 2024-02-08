package com.booleanuk.api.controllers;

import com.booleanuk.api.repositories.EmployeeRepository;
import com.booleanuk.api.models.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository employees;

    public EmployeeController() throws SQLException{
        this.employees = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException{
        return this.employees.getAll();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable int id) throws SQLException{
        Employee employee = this.employees.get(id);
        if (employee == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
        }
        return employee;
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable int id, @RequestBody Employee employee) throws SQLException{
        return this.employees.update(id,employee);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) throws SQLException{
        return this.employees.add(employee);
    }

    @DeleteMapping("/{id}")
    public Employee deleteEmployee(@PathVariable int id) throws SQLException{
        return this.employees.delete(id);
    }
}
