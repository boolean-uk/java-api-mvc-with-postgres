package com.booleanuk.api.controllers;

import com.booleanuk.api.models.SalaryGrade;
import com.booleanuk.api.repositories.SalaryGradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/salaries")
public class SalaryGradeController {
    SalaryGradeRepository repository;

    @Autowired
    public SalaryGradeController(SalaryGradeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SalaryGrade> getAllSalaryGrades() {
        log.info("Getting all salary grades");
        return repository.getAllSalaryGrades();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryGrade addSalaryGrade(@RequestBody SalaryGrade salary) {
        try {
            log.info("Adding new " + salary);
            checkValidSalaryGradeObject(salary);
            int grade = repository.addSalaryGrade(salary);
            return repository.getSalaryGrade(grade);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create salary grade. " + e.getMessage());
        }

    }

    @GetMapping("/{grade}")
    public SalaryGrade getSalaryGrade(@PathVariable int grade) {
        try {
            log.info("Getting SalaryGrade(grade=" + grade + ")");
            return repository.getSalaryGrade(grade);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Salary grade " + grade + " not found");
        }
    }

    @PutMapping("/{grade}")
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryGrade updateSalaryGrade(@PathVariable int grade, @RequestBody SalaryGrade salary) {
        try {
            log.info("Updating SalaryGrade(grade=" + grade + ") with values from " + salary);
            checkValidSalaryGradeObject(salary);
            repository.updateSalaryGrade(grade, salary);
            return repository.getSalaryGrade(salary.getGrade());
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Salary grade " + grade + " not found");
        } catch (Exception e) {
            // Catch PSQLExceptions and IllegalArgumentException if the admin decides to set pk to an already used value
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create salary grade. " + e.getMessage());
        }
    }

    @DeleteMapping("/{grade}")
    public SalaryGrade deleteSalaryGrade(@PathVariable int grade) {
        try {
            log.info("Deleting SalaryGrade(grade=" + grade + ")");
            SalaryGrade deletedSalaryGrade = repository.getSalaryGrade(grade);
            repository.deleteSalaryGrade(grade);
            return deletedSalaryGrade;
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Salary grade " + grade + " not found");
        }
    }

    private void checkValidSalaryGradeObject(SalaryGrade salary) {
        // Check that values are not null
        if (Stream.of(salary.getGrade(), salary.getMinSalary(), salary.getMaxSalary()).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Required fields are missing/empty.");
        }
    }
}
