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

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.employeeRepository.getAll();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable int id) throws SQLException {
        Employee employee = this.employeeRepository.getOne(id);

        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with this id");
        }
        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException {
        Employee newOne = this.employeeRepository.add(employee);
        if (newOne == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create new employee with this information");
        }

        else if (employeeRepository.getAll().contains(employee)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The employee exists already");
        }
        else {
            throw new ResponseStatusException(HttpStatus.CREATED, "New employee created successfully");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable (name = "id") int id, @RequestBody Employee employee) throws SQLException {
        //return this.employeeRepository.update(id, employee);
        Employee updatedOne = this.employeeRepository.update(id, employee);
        if (updatedOne == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with this id");
        }
        else {
            throw new ResponseStatusException(HttpStatus.CREATED, "Employee updated successfully");
        }
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable int id) throws SQLException {
        Employee deletedOne = this.employeeRepository.delete(id);

        if (deletedOne == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with this id");
        }
        else {
            throw new ResponseStatusException(HttpStatus.OK, "Employee deleted successfully");
        }
    }
}
