package com.booleanuk.employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping()
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        try {
            return ResponseEntity.ok(employeeRepository.add(employee));
        } catch (Exception e) {

            return new ResponseEntity<>("Could not create employee, please check that all required fields are correct.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public List<Employee> getAllEmployees() throws SQLException {
        try {
            return employeeRepository.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable int id) {
        try {
            return ResponseEntity.ok(employeeRepository.getOne(id));
        } catch (Exception e) {
            return new ResponseEntity<>("No employees with that id where found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id) {
        try {
            return ResponseEntity.ok(employeeRepository.delete(id));
        } catch (Exception e) {

            return new ResponseEntity<>("No employees with that id where found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        try {
            return ResponseEntity.ok(employeeRepository.update(id, employee));
        } catch (Exception e) {

            return new ResponseEntity<>("No employees with that id where found", HttpStatus.NOT_FOUND);
        }
    }

}
