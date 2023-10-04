package com.booleanuk.api.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private EmployeeRepository employee;

    public EmployeeController() throws SQLException {
        this.employee = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.employee.getAll();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable(name = "id") int id) throws SQLException {
        Employee theEmployee = this.employee.get(id);
        return theEmployee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException {
        return this.employee.add(employee);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable (name = "id") int id, @RequestBody Employee employee) throws SQLException {
        Employee updateEmployee = this.employee.get(id);
        return this.employee.update(id, employee);
    }

    @DeleteMapping({"/{id}"})
    public Employee delete(@PathVariable (name = "id") int id) throws SQLException {
        Employee deleteEmployee = this.employee.get(id);
        return this.employee.delete(id);
    }
}
