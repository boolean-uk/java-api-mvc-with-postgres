package com.booleanuk.api.requests;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return createdEmployee;    //Loopa igenom listan för att säkerställa att employee lagts till
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
        return specificEmployee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateEmployee(@PathVariable long id, @RequestBody Employee employee) throws SQLException {
        Employee updatedEmployee = this.employeeRepository.updateEmployee(id, employee);
        return updatedEmployee;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee deleteEmployee(@PathVariable long id) throws SQLException {
        Employee deletedEmployee = this.employeeRepository.deleteEmployee(id);
        return deletedEmployee;
    }
}
