package com.booleanuk.api.requests;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    EmployeeRepository employeeRepository;

    public EmployeeController() {
        employeeRepository = new EmployeeRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = this.employeeRepository.createEmployee(employee);
        return createdEmployee;    //Loopa igenom listan för att säkerställa att employee lagts till
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployees() {
        List<Employee> allEmployees = this.employeeRepository.getEmployees();
        return allEmployees;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        Employee updatedEmployee = this.employeeRepository.updateEmployee(id, employee);
        return updatedEmployee;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getSpecificEmployee(@PathVariable int id) {
        Employee deletedEmployee = this.employeeRepository.getSpecificEmployee(id);
        return deletedEmployee;
    }
}
