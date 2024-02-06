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
        Employee theEmployee = employees.addEmployee(employee);

        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified employee.");
        }
        return theEmployee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable int id, @RequestBody Employee employee) throws SQLException {
        Employee toBeUpdated = employees.getEmployee(id);

        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if (employees.findEmployeeFromName(employee.getName()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to update employee.");
        }

        return employees.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable int id) throws SQLException {
        Employee toBeDeleted = employees.getEmployee(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return employees.delete(id);
    }
}
