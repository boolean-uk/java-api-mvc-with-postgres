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

    @GetMapping("{id}")
    public Employee getOne(@PathVariable int id) throws SQLException {
        Employee employee = this.employees.get(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Employee does not exist!");
        }
        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException {
        Employee createdEmployee = this.employees.add(employee);
        if (createdEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Unable to create the specified Customer");
        }
        return createdEmployee;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable (name = "id") int id, @RequestBody Employee customer) throws SQLException {
        Employee updatedEmployee = this.employees.get(id);
        if (updatedEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employees.update(id, customer);
    }

    @DeleteMapping("{id}")
    public Employee delete(@PathVariable (name = "id") int id) throws SQLException {
        Employee deletedEmployee = this.employees.get(id);
        if (deletedEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employees.delete(id);
    }

}
