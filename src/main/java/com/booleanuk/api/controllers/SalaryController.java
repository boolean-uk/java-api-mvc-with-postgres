package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Salary;
import com.booleanuk.api.repositories.SalaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary")
public class SalaryController {
    private final SalaryRepository repo;

    public SalaryController() {
        this.repo = new SalaryRepository();
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Salary> getSalaries() {
        return repo.getAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary getSalary(@PathVariable(name="id") int id) {
        return repo.get(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary updateSalary(@PathVariable(name="id") int id, @RequestBody Salary salary) {
        return repo.update(id, salary);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary createSalary(@RequestBody Salary salary) {
        return repo.add(salary);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary deleteSalary(@PathVariable(name="id") int id) {
        return repo.delete(id);
    }
}
