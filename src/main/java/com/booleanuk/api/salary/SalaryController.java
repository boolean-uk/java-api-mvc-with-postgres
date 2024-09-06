package com.booleanuk.api.salary;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/salaries")
public class SalaryController {
    private SalaryRepository repository;

    public SalaryController() throws SQLException {
        this.repository = new SalaryRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryDTO create(@RequestBody SalaryDTO salary) throws SQLException {
        try {
            return this.repository.add(salary);
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Could not create salary, please check all required fields are correct.");
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SalaryDTO> getAll() throws SQLException {
        return this.repository.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SalaryDTO get(@PathVariable(name = "id") int id) throws SQLException {
        return this.repository.get(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryDTO update(@PathVariable (name = "id") int id, @RequestBody Salary salary) {
        try {
            return this.repository.update(id, salary);
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Could not update the salary, please check all required fields are correct.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SalaryDTO delete(@PathVariable (name = "id") int id) throws SQLException {
        return this.repository.delete(id);
    }
}
