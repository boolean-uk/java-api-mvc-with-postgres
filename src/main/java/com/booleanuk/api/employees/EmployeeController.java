package com.booleanuk.api.employees;

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
        return employees.getAll();
    }

    @GetMapping("{id}")
    public Employee getOne(@PathVariable int id) throws SQLException {
        Employee employee = employees.getOne(id);
        if (employee != null)
            return employee;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Computer says \"I can't find the employee :.( \"");
    }

    @PostMapping
    public Employee add(@RequestBody Employee employee) throws SQLException {
        Employee addedEmployee = employees.add(employee);
        if (addedEmployee != null)
            return addedEmployee;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Computer says \"I can't find the employee you tried to add :.( \"");
    }

    @PutMapping("{id}")
    public Employee update(@PathVariable int id, @RequestBody Employee employee) throws SQLException {
        Employee updatedEmployee = employees.update(id, employee);
        if (updatedEmployee != null)
            return updatedEmployee;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Computer says \"I can't find the employee you tried to update :.( \"");
    }

    @DeleteMapping("{id}")
    public Employee delete(@PathVariable int id) throws SQLException {
        Employee deletedEmployee = employees.delete(id);
        if (deletedEmployee != null)
            return deletedEmployee;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Computer says \"I can't find the employee you tried to delete :.( \" which sounds ironic but I wanted to see it one last time");
    }
}
