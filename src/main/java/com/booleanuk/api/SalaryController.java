package com.booleanuk.api;

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

    @GetMapping("{id}")
    public Salary getOne(@PathVariable int id) throws SQLException {
        Salary salary = this.salaryRepository.getOne(id);

        // extensions
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary with that id found");
        }
        return salary;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary create(@RequestBody Salary salary) throws SQLException {
        Salary theSalary = this.salaryRepository.add(salary);

        // extensions
        if (theSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified salary");
        }
        return theSalary;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable (name = "id") int id, @RequestBody Salary salary) throws SQLException {
        Salary toBeUpdated = this.salaryRepository.getOne(id);

        // extension
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given employee does not exist");
        }
        return this.salaryRepository.update(id, salary);
    }

    // possible to refactor
    @DeleteMapping("/{id}")
    public Salary delete(@PathVariable (name = "id") int id) throws SQLException {
        Salary toBeDeleted = this.salaryRepository.getOne(id);

        // extension
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given employee dies not exist");
        }
        return this.salaryRepository.delete(id);
    }
}
