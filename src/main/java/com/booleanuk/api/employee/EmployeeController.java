package com.booleanuk.api.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository employees;

    public EmployeeController() throws SQLException {
        this.employees = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.employees.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException {
        if (employee.getName() == null || employee.getJobName() == null || employee.getDepartment() == null || employee.getSalaryGrade() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        Employee theEmployee =  this.employees.add(employee);
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        return theEmployee;
    }

    @GetMapping("/{id}")
    public Employee get(@PathVariable(name = "id") int id) throws SQLException {
        Employee theEmployee =  this.employees.get(id);
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return theEmployee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable(name="id") int id, @RequestBody Employee employee) throws SQLException {
        if (employee.getName() == null || employee.getJobName() == null || employee.getDepartment() == null || employee.getSalaryGrade() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        Employee theEmployee = this.employees.update(id, employee);
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return theEmployee;
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable(name="id") int id) throws SQLException {
        Employee theEmployee =  this.employees.delete(id);
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return theEmployee;
    }
}
