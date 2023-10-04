package com.booleanuk.api.salaries;

import com.booleanuk.api.employee.Employee;
import com.booleanuk.api.employee.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    @Autowired
    private SalaryRepo salaryRepo;

    @GetMapping
    public ResponseEntity<ArrayList<Salary>> getAllSalaries() {
        try {
            ArrayList<Salary> salaries = salaryRepo.getAllData();
            return ResponseEntity.ok(salaries);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salary> getASallary(@PathVariable int id) {
        try {
            Salary salary = salaryRepo.getOne(id);
            if (salary != null) {
                return ResponseEntity.ok(salary);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping
    public ResponseEntity<Salary> createSalaey(@RequestBody Salary salary) {
        try {
            Salary salary1 = salaryRepo.add(salary);
            if (salary1 != null) {
                return ResponseEntity.ok(salary);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Salary> updateSalary(@PathVariable int id, @RequestBody Salary salary) {
        try {
            Salary salary1 = salaryRepo.update(id, salary);
            if (salary1 != null) {
                return ResponseEntity.ok(salary1);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Salary> deleteSalary(@PathVariable int id) {
        try {
            Salary salary = salaryRepo.delete(id);
            if (salary != null) {
                return ResponseEntity.ok(salary);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
