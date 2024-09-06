package com.booleanuk.api;

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
    public List<Employee> getALl() throws SQLException{
        return this.employeeRepository.getAll();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable int id) throws SQLException {
        Employee employee = this.employeeRepository.getOne(id);
        if(employee == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find the employee");
        }
        return employee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable int id, @RequestBody Employee employee) throws SQLException{
        Employee employeeToUpdate = this.employeeRepository.getOne(id);
        if(employeeToUpdate == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND");
        }
        System.out.println(employeeToUpdate);
        return this.employeeRepository.update(id, employee);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException{
        Employee theEmployee = this.employeeRepository.add(employee);
        if(theEmployee == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create this employee");
        }
        return theEmployee;
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable int id) throws SQLException {
        Employee employeeToDelete = this.employeeRepository.getOne(id);
        if(employeeToDelete == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
        }
        return this.employeeRepository.delete(id);
    }


}
