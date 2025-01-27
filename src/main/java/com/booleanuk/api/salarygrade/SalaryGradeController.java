package com.booleanuk.api.salarygrade;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/salaries")
public class SalaryGradeController {
    private SalaryGradeRepository salaryGrades;

    public SalaryGradeController() throws SQLException {
        this.salaryGrades = new SalaryGradeRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryGrade createOne(@RequestBody SalaryGrade salaryGrade) throws SQLException {
        SalaryGrade e = this.salaryGrades.add(salaryGrade);
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Something went wrong; did not create salaryGrade.");
        }
        return e;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SalaryGrade> getAll() throws SQLException {
        List<SalaryGrade> es = salaryGrades.getAll();
        if (es.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salaryGrades found.");
        }
        return es;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SalaryGrade getOne(@PathVariable(name="id") int id) throws SQLException {
        SalaryGrade e = salaryGrades.getOne(id);
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SalaryGrade not found.");
        }
        return e;
    }

    @PutMapping("/{id}")
    public SalaryGrade updateOne(@PathVariable(name="id") int id, @RequestBody SalaryGrade salaryGrade) throws SQLException {
        SalaryGrade e = salaryGrades.updateOne(id, salaryGrade);
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SalaryGrade not found.");
        }
        return e;
    }

    @DeleteMapping("/{id}")
    public SalaryGrade deleteOne(@PathVariable(name="id") int id) throws SQLException {
        SalaryGrade e = salaryGrades.deleteOne(id);
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SalaryGrade not found.");
        }
        return e;
    }
}
