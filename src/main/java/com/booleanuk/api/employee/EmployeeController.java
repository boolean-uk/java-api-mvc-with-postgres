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
        this.employeeRepository =  new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.employeeRepository.getAll();
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) throws SQLException {
        Employee newEmployee = this.employeeRepository.add(employee);
        if (newEmployee == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create new Employee");
        } else {
            throw new ResponseStatusException(HttpStatus.CREATED, "Successfully created a new Employee");
        }
    }

    @GetMapping("{id}")
    public Employee getOne(@PathVariable int id) throws SQLException {
        Employee employee = this.employeeRepository.getOne(id);
        if (employee == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Employee with the provided id was found");
        } else {
            return employee;
        }
    }

    @PutMapping("{id}")
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee) throws SQLException {
        Employee updatedEmployee = this.employeeRepository.update(id, employee);
        if (updatedEmployee == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with the provided id was found");
        } else {
            throw new ResponseStatusException(HttpStatus.CREATED, "Successfully updated the specified employee");
        }
    }

    @DeleteMapping("{id}")
    public Employee deleteEmployee(@PathVariable int id) throws SQLException {
        Employee employee = this.employeeRepository.delete(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with the provided id was found");
        } else {
            throw new ResponseStatusException(HttpStatus.OK, "Successfully deleted the specified employee");
        }
    }
}
