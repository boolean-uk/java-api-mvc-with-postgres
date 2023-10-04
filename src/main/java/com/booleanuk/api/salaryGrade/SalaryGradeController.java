package com.booleanuk.api.salaryGrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salaries")
public class SalaryGradeController {
    @Autowired
    private SalaryGradeRepository salaryGradeRep;

    @GetMapping
    public List<SalaryGrade> getAll() throws SQLException {
        return this.salaryGradeRep.getAll();
    }

    @GetMapping("/{id}")
    public SalaryGrade getOne(@PathVariable(name = "id") long id) throws SQLException {
        SalaryGrade salaryGrade = this.salaryGradeRep.get(id);
        if (salaryGrade == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return salaryGrade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryGrade create(@RequestBody SalaryGrade salaryGrade) throws SQLException {
        SalaryGrade theSalaryGrade = this.salaryGradeRep.add(salaryGrade);
        if (theSalaryGrade == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Salary Grade");
        }
        return theSalaryGrade;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryGrade update(@PathVariable (name = "id") long id, @RequestBody SalaryGrade salaryGrade) throws SQLException {
        SalaryGrade toBeUpdated = this.salaryGradeRep.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.salaryGradeRep.update(id, salaryGrade);
    }

    @DeleteMapping("/{id}")
    public SalaryGrade delete(@PathVariable (name = "id") long id) throws SQLException {
        SalaryGrade toBeDeleted = this.salaryGradeRep.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.salaryGradeRep.delete(id);
    }
}
