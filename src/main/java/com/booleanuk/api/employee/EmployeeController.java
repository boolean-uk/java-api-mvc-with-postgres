package com.booleanuk.api.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.EnumMap;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeRepository employeeRepo;

    public EmployeeController() throws SQLException {
        this.employeeRepo = new EmployeeRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create (@RequestBody Employee employee) throws SQLException {
        Employee createdEmployee = employeeRepo.create(employee);
        if (createdEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create employee, place chech all required fields are correct");
        }
        return createdEmployee;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAll() throws SQLException {
        return employeeRepo.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getOne(long id) throws SQLException {
        Employee oneEmployee = employeeRepo.getOne(id);
        if ( oneEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No employee with that id were found");
        }
        return oneEmployee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable long id, @RequestBody Employee employee) throws SQLException {
        Employee updatedEmployee = employeeRepo.update(id,employee);
        if ( updatedEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No employees with that id were found");
        }
        return updatedEmployee;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee delete(@PathVariable long id) throws SQLException {
        Employee deletedEmployee = employeeRepo.delete(id);
        if ( deletedEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No employee with that id were found");
        }
        return deletedEmployee;
    }


}
