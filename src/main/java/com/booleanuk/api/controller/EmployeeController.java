package com.booleanuk.api.controller;

import com.booleanuk.api.model.Employee;
import com.booleanuk.api.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {


    private EmployeeRepository employeeRepository;

    public EmployeeController() throws SQLException{
        this.employeeRepository = new EmployeeRepository();
    }



    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.employeeRepository.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException {
        Employee returnEmployee = this.employeeRepository.add(employee);
        if (returnEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while creating Employee");
        }
        return employee;
    }
    @GetMapping("/{id}")
    public Employee getOne(@PathVariable(name = "id") int id) throws SQLException {
        Employee employee = this.employeeRepository.get(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return employee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable (name = "id") int id, @RequestBody Employee employee) throws SQLException {
        Employee toBeUpdated = this.employeeRepository.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employeeRepository.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable (name = "id") int id) throws SQLException {
        Employee toBeDeleted = this.employeeRepository.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employeeRepository.delete(id);
    }

}
