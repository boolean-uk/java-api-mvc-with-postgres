package com.booleanuk.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository employees;

    public EmployeeController() throws SQLException{
        this.employees = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll(){
        return employees.getEmployees();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable int id){
        Employee employee = employees.getEmployee(id);

        if (employee == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException{
        Employee e =
    }
}
