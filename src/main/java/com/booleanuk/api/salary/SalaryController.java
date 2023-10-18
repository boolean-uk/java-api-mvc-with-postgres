package com.booleanuk.api.salary;

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

    @GetMapping("/{grade}")
    public Salary getOne(@PathVariable(name = "grade") String grade) throws SQLException {
        Salary salary = this.salaries.get(grade);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return salary;
    }

    @PutMapping("/{grade}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable (name = "grade") String grade, @RequestBody Salary salary) throws SQLException {
        Salary toBeUpdated = this.salaries.get(grade);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.salaries.update(grade ,salary);
    }

    @DeleteMapping("/{grade}")
    public Salary delete(@PathVariable (name = "grade") String grade) throws SQLException {
        Salary toBeDeleted = this.salaries.get(grade);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.salaries.delete(grade);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Salary create(@RequestBody Salary salary) throws SQLException {
        Salary theSalary = this.salaries.add(salary);
        if (theSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Salary");
        }
        return theSalary;
    }
}
