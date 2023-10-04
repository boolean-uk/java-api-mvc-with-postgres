package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Employee;
import com.booleanuk.api.repositories.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeRepository repository;

    public EmployeeController() throws SQLException {
        this.repository = new EmployeeRepository();
    }

    // --------------------------------------------- //
    // -------------------- API -------------------- //
    // --------------------------------------------- //

    //region // POST //
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        Employee newEmployee;

        try { newEmployee = this.repository.add(employee); }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not create employee, please check all required fields are correct."
            );
        }

        if(newEmployee == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not create employee, please check all required fields are correct."
            );
        }

        return newEmployee;
    }
    //endregion

    //region // GET //
    @GetMapping
    public List<Employee> getAll() {
        List<Employee> employees;

        try { employees = this.repository.getAll(); }
        catch (Exception ex) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST); }

        if(employees.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No employees matching the criteria were found"
            );
        }

        return employees;
    }

    @GetMapping("/{id}")
    public Employee get(@PathVariable int id) {
        Employee employee;

        try { employee = this.repository.get(id); }
        catch (Exception ex) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST); }

        if(employee == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No employees with that id were found"
            );
        }

        return employee;
    }
    //endregion

    //region // PUT //
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee put(@PathVariable int id, @RequestBody Employee employee) {
        Employee updatedEmployee;

        try { updatedEmployee = this.repository.update(id, employee); }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not update the employee, please check all required fields are correct."
            );
        }

        if(updatedEmployee == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No employees with that id were found"
            );
        }

        return updatedEmployee;
    }
    //endregion

    //region // DELETE //
    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable int id) {
        Employee deletedEmployee;

        try { deletedEmployee = this.repository.delete(id); }
        catch (Exception ex) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST); }

        if(deletedEmployee == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No employees with that id were found"
            );
        }

        return deletedEmployee;
    }
    //endregion
}
