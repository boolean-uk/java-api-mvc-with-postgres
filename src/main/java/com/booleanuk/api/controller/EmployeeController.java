package com.booleanuk.api.controller;

import com.booleanuk.api.model.Employee;
import com.booleanuk.api.repository.EmployeeRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository empRepository;

    public EmployeeController() throws SQLException {
        this.empRepository = new EmployeeRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) throws SQLException{
       empRepository.createEmployee(employee);
       return employee;
    }

    @GetMapping
    public List<Employee> getEmployees() throws SQLException{
        return empRepository.getEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getSpecificEmployee(@PathVariable int id) throws SQLException {
        Employee e = empRepository.getSpecificEmployee(id);
        return e != null ?
                ResponseEntity.ok(e):
                ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable int id) throws SQLException{
        Employee e = empRepository.getSpecificEmployee(id);
        if (e == null) {
            ResponseEntity.badRequest().build();
        }
        employee.setId(id);
        Employee updatedEmployee = empRepository.updateEmployee(employee);

        return e != null ?
                ResponseEntity.ok(updatedEmployee):
                ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable int id) throws SQLException {
        Employee e = empRepository.deleteEmployee(id);
        return e != null ?
                ResponseEntity.ok(e):
                ResponseEntity.notFound().build();
    }

}
