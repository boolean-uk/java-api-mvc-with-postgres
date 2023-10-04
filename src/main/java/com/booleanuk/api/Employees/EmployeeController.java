package com.booleanuk.api.Employees;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employees;

    public EmployeeController() {
        employees = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAllEmployees() throws SQLException {
        return this.employees.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee postEmployee(@RequestBody Employee employee) throws SQLException {
        Employee theEmployee = this.employees.create(employee);
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Could not create employee, please check all required fields are correct.");
        }
        return employee;
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable int id) throws SQLException {
        Employee theEmployee = this.employees.get(id);
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "No employees with that id were found.");
        }
        return theEmployee;
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee) throws SQLException {
        Employee theEmployee = this.employees.update(id, employee);
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "No employee with that id was found.");
        }
        return theEmployee;
    }

    @DeleteMapping("/{id}")
    public Employee deleteEmployee(@PathVariable int id) throws SQLException {
        Employee theEmployee = this.employees.delete(id);
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "No employee with that id was found.");
        }
        return theEmployee;
    }
}
