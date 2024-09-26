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
        return salaries.getAll();
    }

    @GetMapping("/{id}")
    public Salary getOne(@PathVariable(name = "id") long id) throws SQLException {
        Salary salary = salaries.get(id);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching that id were found");
        }
        return salary;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary create(@RequestBody Salary salary) throws SQLException {
        Salary theSalary = salaries.add(salary);
        if (theSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create the new salary, please check all required fields are correct.");
        }
        return theSalary;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable (name = "id") long id, @RequestBody Salary salary) throws SQLException {
        Salary toBeUpdated = salaries.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching that id were found");
        }
        Salary updatedSalary = salaries.update(id, salary);
        if (updatedSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the salary, please check all required fields are correct.");
        }
        return updatedSalary;
    }

    @DeleteMapping("/{id}")
    public Salary delete(@PathVariable (name = "id") long id) throws SQLException {
        Salary toBeDeleted = salaries.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching that id were found");
        }
        return salaries.delete(id);
    }
}

