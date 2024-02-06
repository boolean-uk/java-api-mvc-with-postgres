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
    public Employee createEmployee(@RequestBody Employee employee) throws SQLException {
        Employee createdEmployee = this.employeeRepository.createEmployee(employee);

        if(createdEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create employee, please check all required fields are correct.");
        }

        return createdEmployee;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployees() throws SQLException {
        List<Employee> allEmployees = this.employeeRepository.getEmployees();
        return allEmployees;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getSpecificEmployee(@PathVariable long id) throws SQLException {
        Employee specificEmployee = this.employeeRepository.getSpecificEmployee(id);

        if(specificEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that id were found.");
        }

        return specificEmployee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateEmployee(@PathVariable long id, @RequestBody Employee employee) throws SQLException {
        if(employee.getName() == null || employee.getJobName() == null || employee.getSalary_id() < 0 || employee.getDepartment_id() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the employee, please check all required fields are correct.");
        }

        Employee updatedEmployee = this.employeeRepository.updateEmployee(id, employee);

        if(updatedEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with that id was found.");
        }

        return updatedEmployee;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee deleteEmployee(@PathVariable long id) throws SQLException {
        Employee deletedEmployee = this.employeeRepository.deleteEmployee(id);

        if(deletedEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with that id was found.");
        }

        return deletedEmployee;
    }
}
