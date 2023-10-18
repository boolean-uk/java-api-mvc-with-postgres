package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Salary;
import com.booleanuk.api.repositories.EmployeeRepository;
import com.booleanuk.api.repositories.SalaryRepo;
import com.booleanuk.api.sqlUtils.SQLConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("salaries")
public class SalaryController {

    private SalaryRepo salaryRepo;

    public SalaryController() throws SQLException {
        this.salaryRepo = new SalaryRepo();
    }

    @GetMapping
    public List<Salary> getAll() throws SQLException {
        return this.salaryRepo.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salary> get(@PathVariable long id) throws SQLException {
        return Optional.ofNullable(this.salaryRepo.get(id))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching that id were found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Salary> update(@PathVariable long id, @RequestBody Salary salary) throws SQLException {
        Salary toBeUpdated = this.salaryRepo.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching that id were found");
        }
        return Optional.ofNullable(this.salaryRepo.update(id, salary))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the salary grade, please check all required fields are correct."));
    }
    @PostMapping
    public ResponseEntity<Salary> create(@RequestBody Salary salary) throws SQLException {
        return Optional.ofNullable(this.salaryRepo.add(salary))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create a new salary grade, please check all required fields are correct."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Salary> delete(@PathVariable long id) throws SQLException {
        Salary toBeDeleted = this.salaryRepo.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching that id were found");
        }
        return ResponseEntity.ok(this.salaryRepo.delete(id));
    }

}
