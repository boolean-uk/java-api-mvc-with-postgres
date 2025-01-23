package com.booleanuk.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    private SalaryRepository salaryRepo;

    public SalaryController() throws SQLException {
        this.salaryRepo = new SalaryRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary create (@RequestBody Salary salary) throws SQLException {
        Salary createdSalary = salaryRepo.createSalary(salary);
        if (createdSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create salary, place chech all required fields are correct");
        }
        return createdSalary;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Salary> getAll() throws SQLException {
        return salaryRepo.getAllSalaries();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary getOne(long id) throws SQLException {
        Salary oneSalary = salaryRepo.getOneSalary(id);
        if ( oneSalary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No salary with that id were found");
        }
        return oneSalary;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable long id, @RequestBody Salary salary) throws SQLException {
        Salary updatedSalary = salaryRepo.updateSalary(id,salary);
        if ( updatedSalary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No salary with that id were found");
        }
        return updatedSalary;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary delete(@PathVariable long id) throws SQLException {
        Salary deletedSalary = salaryRepo.deleteSalary(id);
        if ( deletedSalary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No salary with that id were found");
        }
        return deletedSalary;
    }
}
