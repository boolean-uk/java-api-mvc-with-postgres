package com.booleanuk.api.Controllers;

import com.booleanuk.api.Models.Department;
import com.booleanuk.api.Models.Salary;
import com.booleanuk.api.Repositories.SalaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salaries")
public class SalaryController {
    private final SalaryRepository salaryRepository;

    public SalaryController(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    @GetMapping
    public List<Salary> getAllSalaries() throws SQLException {
        return salaryRepository.getAllSalaries();
    }

    @GetMapping("/{id}")
    public Salary getSalaryById(@PathVariable long id) throws SQLException {
        return salaryRepository.getSalaryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary createSalary(@RequestBody Salary salary) {
        return salaryRepository.createSalary(salary);
    }

    @PutMapping("/{id}")
    public Salary updateSalary(@PathVariable long id, @RequestBody Salary salary) {
        try {
            Salary updatedSalary = salaryRepository.updateSalary(id, salary);
            if (updatedSalary != null) {
                return updatedSalary;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Salary with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update salary with ID " + id);
        }
    }

    @DeleteMapping("/{id}")
    public Salary deleteSalary(@PathVariable long id) throws SQLException {
        Salary toBeDeleted = this.salaryRepository.getSalaryById(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.salaryRepository.deleteSalary(id);
    }
}
