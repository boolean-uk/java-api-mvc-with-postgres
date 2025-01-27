package com.booleanuk.api.employee;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createOne(@RequestBody Employee employee) throws SQLException {
        Employee e = this.employees.add(employee);
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Something went wrong; did not create employee.");
        }
        return e;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAll() throws SQLException {
        List<Employee> es = employees.getAll();
        if (es.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees found.");
        }
        return es;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getOne(@PathVariable(name="id") int id) throws SQLException {
        Employee e = employees.getOne(id);
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found.");
        }
        return e;
    }

    @PutMapping("/{id}")
    public Employee updateOne(@PathVariable(name="id") int id, @RequestBody Employee employee) throws SQLException {
        Employee e = employees.updateOne(id, employee);
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found.");
        }
        return e;
    }

    @DeleteMapping("/{id}")
    public Employee deleteOne(@PathVariable(name="id") int id) throws SQLException {
        Employee e = employees.deleteOne(id);
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found.");
        }
        return e;
    }
}
