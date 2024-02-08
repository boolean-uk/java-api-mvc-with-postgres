package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Employee;
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
    private SalaryRepository salaries;

    public SalaryController() throws SQLException {
        this.salaries = new SalaryRepository();
    }

    @GetMapping
    public List<Salary> getAll() throws SQLException{
        return this.salaries.getAll();
    }

    @GetMapping("/{id}")
    public Salary getOne(@PathVariable int id) throws SQLException{
        Salary salary = this.salaries.get(id);
        if (salary == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
        }
        return salary;
    }

    @PutMapping("/{id}")
    public Salary update(@PathVariable int id, @RequestBody Salary salary) throws SQLException{
        return this.salaries.update(id,salary);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary addEmployee(@RequestBody Salary salary) throws SQLException{
        return this.salaries.add(salary);
    }

    @DeleteMapping("/{id}")
    public Salary deleteEmployee(@PathVariable int id) throws SQLException{
        return this.salaries.delete(id);
    }
}
