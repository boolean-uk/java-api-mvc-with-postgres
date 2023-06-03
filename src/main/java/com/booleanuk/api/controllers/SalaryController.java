package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Salary;
import com.booleanuk.api.repositories.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
@RestController
@RequestMapping("salaries")
public class SalaryController {
    @Autowired
    private SalaryRepository salaryRepository;
    @GetMapping()
    public List<Salary> getAll() throws SQLException {
        return salaryRepository.getAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary getById(@PathVariable(name="id") int id) throws SQLException {
        return salaryRepository.get(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary create(@RequestBody Salary salary) throws SQLException {
        Salary theSalary = this.salaryRepository.add(salary);
        if (theSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Salary");
        }
        return theSalary;
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable(name="id") int id,@RequestBody Salary salary) throws SQLException {
        Salary toBeUpdated = this.salaryRepository.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.salaryRepository.update(id, salary);
    }
    @DeleteMapping("/{id}")
    public Salary delete(@PathVariable (name = "id") long id) throws SQLException {
        Salary toBeDeleted = this.salaryRepository.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.salaryRepository.delete(id);
    }
}
