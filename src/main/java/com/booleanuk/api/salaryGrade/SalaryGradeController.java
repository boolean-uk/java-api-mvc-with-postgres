package com.booleanuk.api.salaryGrade;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salarygrades")
public class SalaryGradeController {

    private SalaryGradeRepository salaryGradeRepository;

    public SalaryGradeController() throws SQLException {
        this.salaryGradeRepository = new SalaryGradeRepository();
    }

    @GetMapping
    public List<SalaryGrade> getAll() throws SQLException {
        return this.salaryGradeRepository.getAll();
    }

    @GetMapping("/{id}")
    public SalaryGrade getOne(@PathVariable int id) throws SQLException {
        SalaryGrade salaryGrade = this.salaryGradeRepository.getOne(id);

        if (salaryGrade == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grade with this id");
        }
        return salaryGrade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryGrade create(@RequestBody SalaryGrade salaryGrade) throws SQLException {
        SalaryGrade newOne = this.salaryGradeRepository.add(salaryGrade);
        if (newOne == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create new salary grade with this information");
        } else if (salaryGradeRepository.getAll().contains(salaryGrade)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The salary grade exists already");
        } else {
            throw new ResponseStatusException(HttpStatus.CREATED, "New salary grade created successfully");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryGrade update(@PathVariable(name = "id") int id, @RequestBody SalaryGrade salaryGrade) throws SQLException {
        SalaryGrade updatedOne = this.salaryGradeRepository.update(id, salaryGrade);
        if (updatedOne == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grade with this id");
        } else {
            throw new ResponseStatusException(HttpStatus.CREATED, "Salary grade updated successfully");
        }
    }

    @DeleteMapping("/{id}")
    public SalaryGrade delete(@PathVariable int id) throws SQLException {
        SalaryGrade deletedOne = this.salaryGradeRepository.delete(id);

        if (deletedOne == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grade with this id");
        }
        else {
            throw new ResponseStatusException(HttpStatus.OK, "Salary grade deleted successfully");
        }
    }
}

