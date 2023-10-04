package com.booleanuk.api.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepo employeeRepo;

    @GetMapping
    public ResponseEntity<ArrayList<Employee>> getAllEmployees() {
        try {
            ArrayList<Employee> employees = employeeRepo.getAllData();
            return ResponseEntity.ok(employees);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getAnEmployee(@PathVariable int id) {
        try {
            Employee employee = employeeRepo.getOne(id);
            if (employee != null) {
                return ResponseEntity.ok(employee);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping
    public ResponseEntity<Employee> createAnEmployee(@RequestBody Employee employee) {
        try {
            Employee employee1 = employeeRepo.add(employee);
            return ResponseEntity.ok(employee1);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateAnEmployee(@PathVariable int id, @RequestBody Employee employee) {
        try {
            Employee employee1 = employeeRepo.update(id, employee);
            if (employee1 != null) {
                return ResponseEntity.ok(employee1);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteAnEmployee(@PathVariable int id) {
        try {
            Employee employee = employeeRepo.delete(id);
            if (employee != null) {
                return ResponseEntity.ok(employee);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
