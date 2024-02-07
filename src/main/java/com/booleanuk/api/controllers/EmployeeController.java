package com.booleanuk.api.controllers;

import com.booleanuk.api.modules.Employee;
import com.booleanuk.api.repositories.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    EmployeeRepository _repository;

    public EmployeeController() {
        try {
            _repository = new EmployeeRepository();
        }
        catch (SQLException e) {
            System.out.println("[ERROR] : " + e.getMessage());
        }
    }

    @GetMapping
    public List<Employee> getAll() {
        try {
            return _repository.getAll();
        }
        catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        try {
            return _repository.create(employee);
        }
        catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee get(@PathVariable int id) {
        try {
            return _repository.get(id);
        }
        catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable int id, @RequestBody Employee employee) {
        try {
            return _repository.update(id, employee);
        }
        catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee remove(@PathVariable int id) {
        try {

            return _repository.delete(id);
        }
        catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
