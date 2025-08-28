package com.booleanuk.api.model.controller;

import com.booleanuk.api.model.dto.EmployeeDto;
import com.booleanuk.api.model.pojo.Employee;
import com.booleanuk.api.model.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    EmployeeRepository repository;

    public EmployeeController() throws SQLException {
        this.repository = new EmployeeRepository();
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(this.repository.getAll());
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Could not get all employees: " + e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(this.repository.getById(id));
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Could not get employee by id: " + e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody EmployeeDto employeeDto) {
        try {
            return ResponseEntity.ok(this.repository.update(id,
                    new Employee(
                            employeeDto.getName(),
                            employeeDto.getJobName(),
                            employeeDto.getSalaryGrade(),
                            employeeDto.getDepartment())));
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Could not update employee: " + e);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody EmployeeDto employeeDto) {
        try {
            return ResponseEntity.ok(this.repository.add(
                    new Employee(
                            employeeDto.getName(),
                            employeeDto.getJobName(),
                            employeeDto.getSalaryGrade(),
                            employeeDto.getDepartment())));
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Could not create employee: " + e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            return ResponseEntity.ok(this.repository.delete(id));
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Could not delete employee: " + e);
        }
    }

}
