package com.booleanuk.api.salarygrades;


import com.booleanuk.api.exceptions.InvalidRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salaries")
public class SalaryGradeController {

    private SalaryGradeRepository repository;

    public SalaryGradeController() throws SQLException {
        this.repository = new SalaryGradeRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryGrade create(@RequestBody SalaryGrade salaryGrade) throws SQLException {
        SalaryGrade salaryGradeObject = null;
        try {
            salaryGradeObject = this.repository.create(salaryGrade);
        } catch (InvalidRequest e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return salaryGradeObject;
    }

    @GetMapping
    public List<SalaryGrade> getAll() throws SQLException {
        return this.repository.getAll();
    }

    @GetMapping("/{id}")
    public SalaryGrade getSpecific(@PathVariable long id) throws SQLException {
        SalaryGrade salaryGrade = this.repository.getSpecific(id);
        if (salaryGrade == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return salaryGrade;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryGrade update(@PathVariable (name = "id") long id, @RequestBody SalaryGrade salaryGrade) throws SQLException {
        SalaryGrade toBeUpdated = this.repository.getSpecific(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.repository.update(id, salaryGrade);
    }

    @DeleteMapping("/{id}")
    public SalaryGrade delete(@PathVariable (name = "id") long id) throws SQLException {
        SalaryGrade toBeDeleted = this.repository.getSpecific(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.repository.delete(id);
    }
}
