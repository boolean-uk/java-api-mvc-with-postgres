package com.booleanuk.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("{id}")
    public Employee getOne(@PathVariable int id) throws SQLException {
        Employee employee = this.employeeRepository.getOne(id);

        // extensions
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with that id found");
        }
        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException {
        Employee theEmployee = this.employeeRepository.add(employee);

        // extensions
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Customer");
        }
        return theEmployee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable (name = "id") int id, @RequestBody Employee employee) throws SQLException {
        Employee toBeUpdated = this.employeeRepository.getOne(id);

        // extension
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employeeRepository.update(id, employee);
    }

    // possible to refactor
    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable (name = "id") int id) throws SQLException {
        Employee toBeDeleted = this.employeeRepository.getOne(id);

        // extension
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employeeRepository.delete(id);
    }
}
