package com.booleanuk.api.controller;

import com.booleanuk.api.model.Employee;
import com.booleanuk.api.repository.EmployeeRepository;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee add(@RequestBody Employee toAdd) throws SQLException {
        Employee added = this.employees.add(toAdd);
        if (added != null) return added;
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create employee.");
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.employees.getall();
    }

    @GetMapping("{id}")
    public Employee getOne(@PathVariable long id) throws SQLException {
        Employee e = this.employees.getOne(id);
        if (e != null) return e;
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee with given ID not found.");
    }

    @DeleteMapping("{id}")
    public Employee delete(@PathVariable long id) throws SQLException {
        Employee toDelete = this.employees.getOne(id);
        if (toDelete != null) return this.employees.delete(id);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.");
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable long id, @RequestBody Employee toUpdate) throws SQLException {
        Employee actualToUpdate = this.employees.getOne(id);
        if (actualToUpdate != null) return this.employees.update(id, toUpdate);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.");
    }

}
