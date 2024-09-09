package com.booleanuk.api;


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
    public Salary getOne(@PathVariable(name = "id") String grade) throws SQLException {
        Salary salary = this.salaries.get(grade);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return salary;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary create(@RequestBody Salary Salary) throws SQLException {
        Salary theSalary = this.salaries.add(Salary);
        if (theSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Salary");
        }
        return Salary;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable (name = "id") String grade, @RequestBody Salary salary) throws SQLException {
        Salary toBeUpdated = this.salaries.get(grade);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.salaries.update(grade, salary);
    }

    @DeleteMapping("/{id}")
    public Salary delete(@PathVariable (name = "id") String grade) throws SQLException {
        Salary toBeDeleted = this.salaries.get(grade);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.salaries.delete(grade);
    }


}
