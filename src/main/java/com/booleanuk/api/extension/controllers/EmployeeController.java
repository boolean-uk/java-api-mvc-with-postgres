package com.booleanuk.api.extension.controllers;

import com.booleanuk.api.core.models.Employee;
import com.booleanuk.api.extension.models.ExtensionEmployee;
import com.booleanuk.api.extension.repositories.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController("extension_controller")
@RequestMapping("extension/employees")
public class EmployeeController {
    private final EmployeeRepository repo;

    public EmployeeController() {
        this.repo = new EmployeeRepository();
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployees() {
        List<Employee> employees = repo.getAll();

        if (employees.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees matching the criteria were found");

        return employees;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployee(@PathVariable(name="id") int id) {
        Employee requestedEmployee = repo.get(id);

        if (requestedEmployee == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that id were found");

        return requestedEmployee;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateEmployee(@PathVariable(name="id") int id, @RequestBody ExtensionEmployee employee) {
        Employee updatedEmployee = null;

        try {
            updatedEmployee = repo.update(id, employee);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't update the employee, please check all required fields are correct");
        }

        if (updatedEmployee == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that id were found");

        return updatedEmployee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody ExtensionEmployee employee) {
        Employee createdEmployee = null;

        try {
            createdEmployee =  repo.add(employee);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new employee, please check all required fields are correct");
        }
        System.out.println(createdEmployee == null);
        return createdEmployee;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee deleteEmployee(@PathVariable(name="id") int id) {
        Employee deletedEmployee = repo.delete(id);

        if (deletedEmployee == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that id were found");

        return deletedEmployee;
    }
}
