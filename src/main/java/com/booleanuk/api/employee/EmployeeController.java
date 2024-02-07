package com.booleanuk.api.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository employees;
    public  EmployeeController() throws SQLException{
        this.employees = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException{
        return this.employees.getALl();
    }


    @GetMapping("/{id}")
    public Employee getOne(@PathVariable int id) throws SQLException{
        Employee employee = this.employees.get(id);

        if (employee == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with that ID found");
        }
        return employee;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Employee create(@RequestBody Employee employee) throws SQLException{
        Employee employeeToAdd = this.employees.add(employee);
        if(employeeToAdd == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not add employee");
        }
        return employeeToAdd;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{id}")
    public Employee update(@PathVariable int id, @RequestBody Employee employee) throws SQLException{
        this.getOne(id);
        Employee employeeToUpdate = this.employees.update(id, employee);
        if(employeeToUpdate.getName() == null || employeeToUpdate.getJobName() == null || employeeToUpdate.getDepartment() == null || employeeToUpdate.getSalaryGrade() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the employee, please check that all fields are correct");
        }
        return employeeToUpdate;
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable int id) throws SQLException{
        this.getOne(id);
        Employee employeeToDelete = this.employees.delete(id);
        if(employeeToDelete== null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Computer says no");
        }
        return employeeToDelete;
    }

}

