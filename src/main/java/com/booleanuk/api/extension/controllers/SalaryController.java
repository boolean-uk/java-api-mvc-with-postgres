package com.booleanuk.api.extension.controllers;

import com.booleanuk.api.core.models.Employee;
import com.booleanuk.api.extension.models.Salary;
import com.booleanuk.api.extension.repositories.SalaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("extension/salaries")
public class SalaryController {
    private final SalaryRepository repo;

    public SalaryController() {
        this.repo = new SalaryRepository();
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Salary> getSalaries() {
        List<Salary> salaries = repo.getAll();

        if (salaries.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary matching the criteria were found");

        return salaries;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary getSalary(@PathVariable(name="id") int id) {
        Salary requestedSalary = repo.get(id);

        if (requestedSalary == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary matching that id was found");

        return requestedSalary;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary updateSalary(@PathVariable(name="id") int id, @RequestBody Salary salary) {
        Salary updatedSalary = null;

        try {
            updatedSalary = repo.update(id, salary);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't update the salary grade, please check all required fields are correct");
        }

        if (updatedSalary == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees matching that id was found");

        return updatedSalary;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary createSalary(@RequestBody Salary salary) {
        Salary createdSalary = null;

        try {
            createdSalary =  repo.add(salary);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create new salary, please check all required fields are correct");
        }

        return createdSalary;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary deleteSalary(@PathVariable(name="id") int id) {
        Salary deletedSalary = repo.delete(id);

        if (deletedSalary == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary matching that id was found");

        return deletedSalary;
    }
}
