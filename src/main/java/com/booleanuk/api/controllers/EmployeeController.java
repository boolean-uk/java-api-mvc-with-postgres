package com.booleanuk.api.controllers;

import com.booleanuk.api.repositories.EmployeeRepository;
import com.booleanuk.api.models.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private EmployeeRepository employees;

    public EmployeeController() throws SQLException {
        this.employees = new EmployeeRepository();
    }
    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.employees.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable long id) throws SQLException {
        return Optional.ofNullable(this.employees.get(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable long id, @RequestBody Employee employee) throws SQLException {
        Employee toBeUpdated = this.employees.get(id);
        if (toBeUpdated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.employees.update(id, employee));
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) throws SQLException {
        return Optional.ofNullable(this.employees.add(employee))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> delete(@PathVariable long id) throws SQLException {
        Employee toBeDeleted = this.employees.get(id);
        if (toBeDeleted == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.employees.delete(id));
    }
}
