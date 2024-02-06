package com.booleanuk.api.extension.salaries;

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
        Salary salary = salaries.getOne(id);
        if (salary != null)
            return salary;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No salary grades matching that id were found");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary add(@RequestBody Salary salary) throws SQLException {
        if(salary.getGrade() == null
                || salary.getMinSalary() == 0
                || salary.getMaxSalary() == 0)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not create salary grade, please check all required fields are correct");

        Salary addedSalary = salaries.add(salary);
        if (addedSalary != null)
            return addedSalary;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Computer says \"I can't find the salary grade you tried to add :.( \"");
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable int id, @RequestBody Salary salary) throws SQLException {
        if(salary.getGrade() == null
                || salary.getMinSalary() == 0
                || salary.getMaxSalary() == 0)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not update salary grade, please check all required fields are correct");

        Salary updatedSalary = salaries.update(id, salary);
        if (updatedSalary != null)
            return updatedSalary;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Tried to add salary grade, but can't find it in the database");
    }

    @DeleteMapping("{id}")
    public Salary delete(@PathVariable int id) throws SQLException {
        Salary deletedSalary = salaries.delete(id);
        if (deletedSalary != null)
            return deletedSalary;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No employee with that id was found");
    }

}
