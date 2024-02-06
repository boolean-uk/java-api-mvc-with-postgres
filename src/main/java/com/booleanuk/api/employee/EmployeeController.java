package com.booleanuk.api.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAll() throws SQLException {
        return this.employees.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getOne(@PathVariable int id) throws  SQLException {
        Employee employee = this.employees.getOne(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Computer says no!");
        }
        return employee;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee deleteOne(@PathVariable int id) throws SQLException {
        Employee employee = this.employees.deleteOne(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Computer says no!");
        }
        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createOne(@RequestBody Employee e) throws SQLException {
        Employee employee = this.employees.createOne(e);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Computer says no!");
        }
        return employee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateOne(@PathVariable int id, @RequestBody Employee e) throws SQLException {
        Employee employee = this.employees.updateOne(id, e);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Computer says no!");
        }
        return employee;
    }
}