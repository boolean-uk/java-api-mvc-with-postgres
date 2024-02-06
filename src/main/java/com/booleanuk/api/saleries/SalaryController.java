package com.booleanuk.api.saleries;

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
    public Salary getOne(@PathVariable int id) throws SQLException {
        Salary salary = this.salaries.get(id);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Salary does not exist!");
        }
        return salary;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary create(@RequestBody Salary salary) throws SQLException {
        Salary createdSalary = this.salaries.add(salary);
        if (createdSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Unable to create the specified Salary");
        }
        return createdSalary;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable (name = "id") int id, @RequestBody Salary salary) throws SQLException {
        Salary updatedSalary = this.salaries.get(id);
        if (updatedSalary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.salaries.update(id, salary);
    }

    @DeleteMapping("{id}")
    public Salary delete(@PathVariable (name = "id") int id) throws SQLException {
        Salary deletedSalary = this.salaries.get(id);
        if (deletedSalary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.salaries.delete(id);
    }
}
