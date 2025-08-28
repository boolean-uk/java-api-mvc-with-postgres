package com.booleanuk.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    EmployeeRepository repo = new EmployeeRepository();

    public EmployeeController() throws SQLException {
    }


    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) throws SQLException {
        Employee emp = repo.addEmployee(employee);
        if (emp != null) {
            return ResponseEntity.ok(emp);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() throws SQLException {
        List<Employee> list = repo.getAllEmployees();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getSingleEmployee(@PathVariable long id) throws SQLException {
        Employee employee = repo.getEmployeeByID(id);
        if (employee != null)
            return ResponseEntity.ok().body(employee);
        else
            return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee body) throws SQLException {
        Employee employee = repo.updateEmployeeByID(id, body);
        if (employee != null)
            return ResponseEntity.ok().body(employee);
        else
            return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable long id) throws SQLException {
        Employee employee = repo.deleteEmployeeByID(id);
        if (employee != null)
            return ResponseEntity.ok().body(employee);
        else
            return ResponseEntity.badRequest().build();
    }
}
