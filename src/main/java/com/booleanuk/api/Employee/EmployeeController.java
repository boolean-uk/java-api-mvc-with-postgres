package com.booleanuk.api.Employee;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeRepository employees;

    public EmployeeController() throws SQLException {
        this.employees = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.employees.getAll();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable (name = "id") int id) throws SQLException {
        Employee singledEmployee = this.employees.getOne(id);
        if (singledEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return singledEmployee;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee newEmployee) throws SQLException{
        Employee employee = this.employees.add(newEmployee);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "can't create employee");
        }
        return employee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable (name = "id") int id, @RequestBody Employee employee) throws SQLException {
        Employee toBeUpdated = this.employees.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employees.update(id, employee);
    }


    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable (name = "id") int id) throws SQLException {
        Employee toBeDeleted = this.employees.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employees.delete(id);
    }



}
