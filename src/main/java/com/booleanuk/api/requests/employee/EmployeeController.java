package com.booleanuk.api.requests.employee;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    EmployeeRepository employeeRepository;

    public EmployeeController() throws SQLException {
        employeeRepository = new EmployeeRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary createEmployee(@RequestBody Salary salary) throws SQLException {
        Salary createdSalary = this.employeeRepository.createEmployee(salary);

        if(createdSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create salary, please check all required fields are correct.");
        }

        return createdSalary;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Salary> getEmployees() throws SQLException {
        List<Salary> allSalaries = this.employeeRepository.getEmployees();
        return allSalaries;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary getSpecificEmployee(@PathVariable long id) throws SQLException {
        Salary specificSalary = this.employeeRepository.getSpecificEmployee(id);

        if(specificSalary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that id were found.");
        }

        return specificSalary;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary updateEmployee(@PathVariable long id, @RequestBody Salary salary) throws SQLException {
        if(salary.getName() == null || salary.getJobName() == null || salary.getSalaryGrade() == null || salary.getDepartment() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the salary, please check all required fields are correct.");
        }

        Salary updatedSalary = this.employeeRepository.updateEmployee(id, salary);

        if(updatedSalary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary with that id was found.");
        }

        return updatedSalary;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary deleteEmployee(@PathVariable long id) throws SQLException {
        Salary deletedSalary = this.employeeRepository.deleteEmployee(id);

        if(deletedSalary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with that id was found.");
        }

        return deletedSalary;
    }
}
