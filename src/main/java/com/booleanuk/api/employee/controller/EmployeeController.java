package com.booleanuk.api.employee.controller;

import com.booleanuk.api.employee.model.pojo.Employee;
import com.booleanuk.api.employee.model.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository repo;

    public EmployeeController(EmployeeRepository repo) {
        this.repo = repo;
    }


    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        try {
            repo.testConnection();
            return ResponseEntity.ok(repo.getAllEmployees());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Employee> getOne(@PathVariable int id) {
        try {
            Employee e = repo.getEmployeeById(id);
            return (e == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(e);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee e) {
        try {
            Employee saved = repo.insertEmployee(e);
            return (saved == null)
                    ? ResponseEntity.badRequest().build()
                    : ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (SQLException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable int id, @RequestBody Employee e) {
        try {
            Employee updated = repo.updateEmployee(id, e);
            return (updated == null)
                    ? ResponseEntity.notFound().build()
                    : ResponseEntity.ok(updated);
        } catch (SQLException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> delete(@PathVariable int id) {
        try {
            Employee deleted = repo.deleteEmployee(id);
            return (deleted == null)
                    ? ResponseEntity.notFound().build()
                    : ResponseEntity.ok(deleted);
        } catch (SQLException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
