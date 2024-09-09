package com.booleanuk.api.controller;

import com.booleanuk.api.model.Employee;
import com.booleanuk.api.model.Salary;
import com.booleanuk.api.repository.SalaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salaries")
public class SalaryController {

    private SalaryRepository salaries;

    public SalaryController() throws SQLException {
        this.salaries = new SalaryRepository();
    }

    @GetMapping
    public List<Salary> getAll() throws SQLException {
        return this.salaries.getAll();
    }

    @GetMapping("{id}")
    public Salary getOne(@PathVariable long id) throws SQLException {
        Salary s = this.salaries.getOne(id);
        if (s != null) return s;
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Salary with given ID not found.");
    }

    @PostMapping
    public Salary add(@RequestBody Salary toAdd) throws SQLException {
        Salary added = this.salaries.add(toAdd);
        if (added != null) return added;
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create salary.");
    }

    @DeleteMapping
    public Salary delete(@PathVariable long id) throws SQLException {
        Salary toDelete = this.salaries.getOne(id);
        if (toDelete != null) return this.salaries.delete(id);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.");
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable long id, @RequestBody Salary toUpdate) throws SQLException {
        Salary actualToUpdate = this.salaries.getOne(id);
        if (actualToUpdate != null) return this.salaries.update(id, toUpdate);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.");
    }

}
