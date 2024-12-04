package com.booleanuk.api.core.employees;

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
        this.employees = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return employees.getAll();
    }

    @GetMapping("{id}")
    public Employee getOne(@PathVariable int id) throws SQLException {
        Employee employee = employees.getOne(id);
        if (employee != null)
            return employee;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No employees with that id were found");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee add(@RequestBody Employee employee) throws SQLException {
        if(employee.getName() == null
        || employee.getJobName() == null
        || employee.getSalaryGrade() == null
        || employee.getDepartment() == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not create employee, please check all required fields are correct");

        Employee addedEmployee = employees.add(employee);
        if (addedEmployee != null)
            return addedEmployee;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Computer says \"I can't find the employee you tried to add :.( \"");
    }

    @PutMapping("{id}")
    public Employee update(@PathVariable int id, @RequestBody Employee employee) throws SQLException {
        if(employee.getName() == null
                || employee.getJobName() == null
                || employee.getSalaryGrade() == null
                || employee.getDepartment() == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not update employee, please check all required fields are correct");

        Employee updatedEmployee = employees.update(id, employee);
        if (updatedEmployee != null)
            return updatedEmployee;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No employee with that id was found");
    }

    @DeleteMapping("{id}")
    public Employee delete(@PathVariable int id) throws SQLException {
        Employee deletedEmployee = employees.delete(id);
        if (deletedEmployee != null)
            return deletedEmployee;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No employee with that id was found");
    }
}
