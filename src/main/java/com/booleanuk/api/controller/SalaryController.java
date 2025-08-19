package com.booleanuk.api.controller;

import com.booleanuk.api.model.Employee;
import com.booleanuk.api.model.Salary;
import com.booleanuk.api.model.SalaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salaries")
public class SalaryController {
    private SalaryRepository salaryRepository;

    public SalaryController() throws SQLException {
        salaryRepository = new SalaryRepository();
    }

    @GetMapping
    public List<Salary> getAll() throws SQLException {
        return this.salaryRepository.getAll();
    }

    @GetMapping("/{id}")
    public Salary getOne(@PathVariable int id) throws SQLException {
        Salary salary = this.salaryRepository.getOne(id);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary with that id were found");
        }
        return salary;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary create(@RequestBody Salary newSalary) throws SQLException {
        Salary theSalary = this.salaryRepository.add(newSalary);
        if (theSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the salary, please check all required fields are correct.");
        }
        return theSalary;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable (name = "id") int id, @RequestBody Salary salary) throws SQLException {
        Salary toBeUpdated = this.salaryRepository.getOne(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary with that id were found");
        }
        return this.salaryRepository.update(id, salary);
    }

    @DeleteMapping("/{id}")
    public Salary delete(@PathVariable int id) throws SQLException {
        Salary toBeDeleted = this.salaryRepository.getOne(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary with that id were found");
        }
        return this.salaryRepository.delete(id);
    }
}
