package com.booleanuk.salaryGrade;

import com.booleanuk.salaryGrade.SalaryGrade;
import com.booleanuk.salaryGrade.SalaryGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salarygrades")
public class SalaryGradeController {

    @Autowired
    private SalaryGradeRepository salaryGradeRepository;

    public SalaryGradeController(SalaryGradeRepository salaryGradeRepository) {
        this.salaryGradeRepository = salaryGradeRepository;
    }

    @PostMapping()
    public ResponseEntity<?> createSalaryGrade(@RequestBody SalaryGrade salaryGrade) {
        try {
            SalaryGrade newSalaryGrade = this.salaryGradeRepository.add(salaryGrade);
            if (newSalaryGrade != null) {
                return new ResponseEntity<>(newSalaryGrade, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to create salary grade", HttpStatus.BAD_REQUEST);
            }
        } catch (SQLException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public List<SalaryGrade> getAllSalaryGrades() throws SQLException {
        return this.salaryGradeRepository.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSalaryGrade(@PathVariable int id) {
        try {
            SalaryGrade salaryGrade = this.salaryGradeRepository.getOne(id);
            if (salaryGrade != null) {
                return new ResponseEntity<>(salaryGrade, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Salary grade not found with id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSalaryGrade(@PathVariable int id) {
        try {
            SalaryGrade deletedSalaryGrade = this.salaryGradeRepository.delete(id);
            if (deletedSalaryGrade != null) {
                return new ResponseEntity<>(deletedSalaryGrade, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Salary grade not found with id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSalaryGrade(@PathVariable int id, @RequestBody SalaryGrade salaryGrade) {
        try {
            SalaryGrade updatedSalaryGrade = this.salaryGradeRepository.update(id, salaryGrade);
            if (updatedSalaryGrade != null) {
                return new ResponseEntity<>(updatedSalaryGrade, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Salary grade not found with id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
