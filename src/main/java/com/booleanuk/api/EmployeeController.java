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

    @GetMapping("/{id}")
    public Employee getById(@PathVariable int id) throws SQLException {
        Employee employee = this.employeeRepository.get(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found!");
        }
        return employee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable int id, @RequestBody Employee employee) throws SQLException {
        if (this.employeeRepository.get(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found!");
        }

        return this.employeeRepository.update(id, employee);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee add(@RequestBody Employee employee) throws SQLException {
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found!");
        }
        return this.employeeRepository.add(employee);
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable int id) throws SQLException {
        Employee employee = this.employeeRepository.get(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found!");
        }
        return this.employeeRepository.delete(id);
    }

}
