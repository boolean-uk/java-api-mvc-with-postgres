package com.booleanuk.api.salary;

import com.booleanuk.api.employee.Employee;
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
    @ResponseStatus(HttpStatus.OK)
    public List<Salary> getAll() throws SQLException {
        return this.salaries.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary getOne(@PathVariable int id) throws  SQLException {
        Salary salary = this.salaries.getOne(id);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Computer says no!");
        }
        return salary;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary deleteOne(@PathVariable int id) throws SQLException {
        Salary salary = this.salaries.deleteOne(id);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Computer says no!");
        }
        return salary;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary createOne(@RequestBody Salary s) throws SQLException {
        Salary salary = this.salaries.createOne(s);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Computer says no!");
        }
        return salary;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary updateOne(@PathVariable int id, @RequestBody Salary s) throws SQLException {
        Salary salary = this.salaries.updateOne(id, s);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Computer says no!");
        }
        return salary;
    }
}