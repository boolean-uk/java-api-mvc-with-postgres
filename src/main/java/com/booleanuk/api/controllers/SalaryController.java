package com.booleanuk.api.controllers;

import com.booleanuk.api.DatabaseManager;
import com.booleanuk.api.models.Department;
import com.booleanuk.api.models.Employee;
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
    private SalaryRepository salaries;

    @Autowired
    public SalaryController(SalaryRepository salaries) throws SQLException {
        this.salaries = salaries;
    }

    @GetMapping
    public List<Salary> getAll() throws SQLException {
        return this.salaries.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary create(@RequestBody Salary salary) throws SQLException {
        if (salary.getGrade() == null || salary.getMinSalary() < 0 || salary.getMaxSalary() < 0 || salary.getMinSalary() > salary.getMaxSalary()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        Salary theSalary =  this.salaries.add(salary);
        if (theSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        return theSalary;
    }

    @GetMapping("/{id}")
    public Salary get(@PathVariable(name = "id") int id) throws SQLException {
        Salary theSalary =  this.salaries.get(id);
        if (theSalary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return theSalary;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable(name="id") int id, @RequestBody Salary salary) throws SQLException {
        if (salary.getGrade() == null || salary.getMinSalary() < 0 || salary.getMaxSalary() < 0 || salary.getMinSalary() > salary.getMaxSalary()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        Salary theSalary = this.salaries.update(id, salary);
        if (theSalary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return theSalary;
    }

    @DeleteMapping("/{id}")
    public Salary delete(@PathVariable(name="id") int id) throws SQLException {
        Salary theSalary =  this.salaries.delete(id);
        if (theSalary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return theSalary;
    }
}
