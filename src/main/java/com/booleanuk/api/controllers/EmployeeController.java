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
    private final EmployeeRepository employees;

    public EmployeeController() throws SQLException {
        this.employees = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.employees.getAll();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable(name = "id") long id) throws SQLException {
        Employee employee = this.employees.getOne(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that id were found");
        }
        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException {
        Employee theEmployee = this.employees.add(employee);
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create employee, please check all required fields are correct.");
        }
        return theEmployee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable(name = "id") long id, @RequestBody Employee employee) throws SQLException {
        Employee toBeUpdated = this.employees.getOne(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with that id was found");
        }
        try {
            Employee updatedEmployee = this.employees.update(id, employee);
            if (updatedEmployee == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the employee, please check all required fields are correct.");
            }
            return updatedEmployee;
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable(name = "id") long id) throws SQLException {
        Employee toBeDeleted = this.employees.getOne(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with that id was found");
        }
        return this.employees.delete(id);
    }

}
