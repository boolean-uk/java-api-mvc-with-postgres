package com.booleanuk.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController() throws SQLException {
        this.employeeRepository = new EmployeeRepository();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = employeeRepository.getAll();
        if (employees.size() > 0) {
            return employees;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees matching the criteria were found");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getOne(@PathVariable(name = "id") int id) throws SQLException {
        Employee employee = employeeRepository.get(id);
        if (employee == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that id were found");
        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        Employee createdEmployee;
        try {
            createdEmployee = employeeRepository.add(employee);
            return createdEmployee;
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create employee, please check all required fields are correct");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable(name = "id") int id, @RequestBody Employee employee) {
        Employee updatedEmployee;
        try {
            updatedEmployee = employeeRepository.update(id, employee);
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the employee, please check all required fields are correct");
        }

        if (updatedEmployee == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that id were found");

        return updatedEmployee;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee delete(@PathVariable(name = "id") int id) {
        Employee deletedEmployee;
        try {
            deletedEmployee = employeeRepository.delete(id);
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (deletedEmployee == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that id were found");
        return deletedEmployee;
    }
}
