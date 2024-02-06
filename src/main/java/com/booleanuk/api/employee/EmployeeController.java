package com.booleanuk.api.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return employee;
    }
}
