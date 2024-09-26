package com.booleanuk.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    private SalaryRepository salary;

    public SalaryController() throws SQLException {
        this.salary = new SalaryRepository();

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Salary> getAllSalaries() throws SQLException {
        return this.salary.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary getOneSalary(@PathVariable(name = "id") long id) throws SQLException {
        Salary salary = this.salary.getOne(id);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return salary;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary createSalary(@RequestBody Salary salary) throws SQLException {
        Salary theSalary = this.salary.add(salary);
        if (theSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified salary");
        }
        return theSalary;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary updateSalary(@PathVariable (name = "id") long id, @RequestBody Salary salary) throws SQLException {
        Salary toBeUpdated = this.salary.getOne(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.salary.update(id, salary);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary deleteSalary(@PathVariable (name = "id") long id) throws SQLException {
        Salary toBeDeleted = this.salary.getOne(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.salary.delete(id);
    }
}
