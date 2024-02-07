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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create employee, please check all required fields are correct.");
        }
        return employee;
    }
    @GetMapping("/{id}")
    public Employee getOne(@PathVariable(name = "id") int id) throws SQLException {
        Employee employee = this.employeeRepository.get(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that id were found");
        }
        return employee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable (name = "id") int id, @RequestBody Employee employee) throws SQLException {
        Employee toBeUpdated = this.employeeRepository.get(id);


        //Feel somewhat weird to call it from employee, might be best to make a file for util like this
        if(!employee.valuesAreAllowed(employee)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the employee, please check all required fields are correct.");

        }

        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with that id was found");
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
