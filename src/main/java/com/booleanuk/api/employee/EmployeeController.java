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
        employeeRepository = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException{
        return this.employeeRepository.getAll();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable(name = "id") long id) throws SQLException {
        Employee employee = this.employeeRepository.get(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException {
        Employee theEmployee = this.employeeRepository.add(employee);
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Employee");
        }
        return theEmployee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable (name = "id") long id, @RequestBody Employee employee) throws SQLException {
        Employee toBeUpdated = this.employeeRepository.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employeeRepository.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable (name = "id") long id) throws SQLException {
        Employee toBeDeleted = this.employeeRepository.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employeeRepository.delete(id);
    }
}
