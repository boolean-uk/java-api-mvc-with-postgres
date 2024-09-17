package com.booleanuk.api.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    public EmployeeController() throws SQLException {
        this.employeeRepository = new EmployeeRepository();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException{
        Employee employee1 = this.employeeRepository.create(employee);
        if (employee1 == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create employee");
        }
        return employee1;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAll() throws SQLException {
        return this.employeeRepository.getAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getOne(@PathVariable int id) throws SQLException {
        Employee employee = this.employeeRepository.getOne(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return employee;
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable int id, @RequestBody Employee employee) throws SQLException {
        Employee toBeUpdated = this.employeeRepository.getOne(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employeeRepository.update(id, employee);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee delete(@PathVariable int id) throws SQLException {
        Employee toBeDeleted = this.employeeRepository.getOne(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employeeRepository.delete(id);
    }
}
