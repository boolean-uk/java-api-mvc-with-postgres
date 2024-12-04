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
    public List<Salary> getAllSalaries() throws SQLException{
        return this.salaries.getAllSalaries();
    }

    @GetMapping("/{id}")
    public Salary getOneSalary(@PathVariable int id) throws SQLException{
        if (this.salaries.getSalary(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching that id where found");
        }else {
            return this.salaries.getSalary(id);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary addSalary(@RequestBody Salary newSalary) throws SQLException {
        Salary salary = this.salaries.addSalary(newSalary);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to create salary");
        }
        return salary;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary updateSalary(@PathVariable int id, @RequestBody Salary updatedSalary) throws SQLException{
        Salary salary = this.salaries.update(id,updatedSalary);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else {
            return salary;
        }
    }

    @DeleteMapping("/{id}")
    public Salary deleteSalary(@PathVariable int id) throws SQLException{
        Salary salary = this.salaries.delete(id);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else {
            return salary;
        }
    }

}
