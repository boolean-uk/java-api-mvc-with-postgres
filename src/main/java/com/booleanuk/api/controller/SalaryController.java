package com.booleanuk.api.controller;

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

    private SalaryRepository salaryRepository;

    public SalaryController() throws SQLException {
        this.salaryRepository = new SalaryRepository();
    }



    @GetMapping
    public List<Salary> getAll() throws SQLException {
        return this.salaryRepository.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary create(@RequestBody Salary salary) throws SQLException {
        Salary returnSalary = this.salaryRepository.add(salary);
        if (returnSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create a new salary grade, please check all required fields are correct.");
        }
        return returnSalary;
    }
    @GetMapping("/{id}")
    public Salary getOne(@PathVariable String id) throws SQLException {
        Salary salary = this.salaryRepository.get(id);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching that id were found");
        }
        return salary;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable String id, @RequestBody Salary salary) throws SQLException {
        Salary toBeUpdated = this.salaryRepository.get(id);


        if(!salary.valuesAreAllowed(salary)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not update the salary grade, please check all required fields are correct.");

        }
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching that id were found");
        }
        return this.salaryRepository.update(id, salary);
    }

    @DeleteMapping("/{id}")
    public Salary delete(@PathVariable String id) throws SQLException {
        Salary toBeDeleted = this.salaryRepository.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grade matching that id was found");
        }
        return this.salaryRepository.delete(id);
    }
}
