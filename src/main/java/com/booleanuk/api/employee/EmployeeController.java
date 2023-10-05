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
        employees = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAllEmployees() throws SQLException {
        return this.employees.getAll();
    }

    @GetMapping("/{id}")
    public Employee getOneEmployee(@PathVariable (name = "id") int id) throws SQLException{
        if (this.employees.getOne(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return this.employees.getOne(id);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee newEmployee) throws SQLException {
        Employee employee = this.employees.add(newEmployee);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable To Create Employee");
        }
        return employee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateEmployee(@PathVariable int id,@RequestBody Employee updatedEmployee) throws SQLException{
        Employee employee = this.employees.update(id,updatedEmployee);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else {
            return employee;
        }
    }

    @DeleteMapping("/{id}")
    public Employee deleteEmployee(@PathVariable int id) throws SQLException{
        Employee employee = this.employees.delete(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else {
            return employee;
        }
    }
}
