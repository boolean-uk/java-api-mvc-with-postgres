package com.booleanuk.api.employees;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository repository;

    public EmployeeController() throws SQLException {
        this.repository = new EmployeeRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException {
        Employee employeeObject = this.repository.create(employee);
        if (employeeObject == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create employee");
        }
        return employeeObject;
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.repository.getAll();
    }

    @GetMapping("/{id}")
    public Employee getSpecific(@PathVariable long id) throws SQLException {
        Employee employee = this.repository.getSpecific(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return employee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable (name = "id") long id, @RequestBody Employee customer) throws SQLException {
        Employee toBeUpdated = this.repository.getSpecific(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.repository.update(id, customer);
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable (name = "id") long id) throws SQLException {
        Employee toBeDeleted = this.repository.getSpecific(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.repository.delete(id);
    }
}
