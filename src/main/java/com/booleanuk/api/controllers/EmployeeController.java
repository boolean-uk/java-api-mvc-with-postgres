package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Employee;
import com.booleanuk.api.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    EmployeeRepository repository;

    @Autowired
    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        log.info("Getting all employees");
        return repository.getAllEmployees();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) {
        try {
            log.info("Adding new " + employee);
            if (Stream.of(employee.getName(), employee.getJobName(), employee.getSalaryGrade(), employee.getDepartment())
                    .anyMatch(field -> field == null || field.isBlank())) {
                throw new IllegalArgumentException("Required fields are missing/empty.");
            }
            int id = repository.addEmployee(employee);
            // Should return employee object with id according to spec
            return repository.getEmployee(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create employee. " + e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable int id) {
        try {
            log.info("Getting Employee(id=" + id + ")");
            return repository.getEmployee(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with id= " + id + " found");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        try {
            log.info("Updating Employee(id=" + id + ") with values from " + employee);
            if (Stream.of(employee.getName(), employee.getJobName(), employee.getSalaryGrade(), employee.getDepartment())
                    .anyMatch(field -> field == null || field.isBlank())) {
                throw new IllegalArgumentException("Required fields are missing/empty.");
            }
            repository.updateEmployee(id, employee);
            return repository.getEmployee(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with id= " + id + " found");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create employee. " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Employee deleteEmployee(@PathVariable int id) {
        try {
            log.info("Deleting Employee(id=" + id + ")");
            Employee deletedEmployee = repository.getEmployee(id);
            repository.deleteEmployee(id);
            return deletedEmployee;
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with id= " + id + " found");
        }
    }
}
