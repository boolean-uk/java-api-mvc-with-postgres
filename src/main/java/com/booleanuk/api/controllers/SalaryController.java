package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Salary;
import com.booleanuk.api.repositories.SalaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salaries")
public class SalaryController {
    private final SalaryRepository salaries;

    public SalaryController() throws SQLException {
        this.salaries = new SalaryRepository();
    }

    @GetMapping
    public List<Salary> getAll() throws SQLException {
        return this.salaries.getAll();
    }

    @GetMapping("/{id}")
    public Salary getOne(@PathVariable(name = "id") long id) throws SQLException {
        Salary salary = this.salaries.getOne(id);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary with that id was found");
        }
        return salary;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary create(@RequestBody Salary salary) throws SQLException {
        Salary theSalary = this.salaries.add(salary);
        if (theSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create salary, please check all required fields are correct.");
        }
        return theSalary;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable(name = "id") long id, @RequestBody Salary salary) throws SQLException {
        Salary toBeUpdated = this.salaries.getOne(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary with that id was found");
        }
        try {
            Salary updatedSalary = this.salaries.update(id, salary);
            if (updatedSalary == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the salary, please check all required fields are correct.");
            }
            return updatedSalary;
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Salary delete(@PathVariable(name = "id") long id) throws SQLException {
        Salary toBeDeleted = this.salaries.getOne(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary with that id was found");
        }
        return this.salaries.delete(id);
    }
}
