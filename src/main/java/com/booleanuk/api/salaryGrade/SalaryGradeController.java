package com.booleanuk.api.salaryGrade;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salaries")
public class SalaryGradeController {

    private SalaryGradeRepository salaryGrades;
    public  SalaryGradeController() throws SQLException {
        this.salaryGrades = new SalaryGradeRepository();
    }

    @GetMapping
    public List<SalaryGrade> getAll() throws SQLException{
        return this.salaryGrades.getALl();
    }

    @GetMapping("/{id}")
    public SalaryGrade getOne(@PathVariable int id) throws SQLException{
        SalaryGrade salaryGrade = this.salaryGrades.get(id);

        if (salaryGrade == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salaryGrade with that ID found");
        }
        return salaryGrade;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SalaryGrade create(@RequestBody SalaryGrade salaryGrade) throws SQLException{
        SalaryGrade salaryGradeToAdd = this.salaryGrades.add(salaryGrade);
        if(salaryGradeToAdd == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not add salaryGrade");
        }
        return salaryGradeToAdd;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{id}")
    public SalaryGrade update(@PathVariable int id, @RequestBody SalaryGrade salaryGrade) throws SQLException{
        this.getOne(id);
        SalaryGrade salaryGradeToUpdate = this.salaryGrades.update(id, salaryGrade);
        if(salaryGradeToUpdate.getGrade() == null || salaryGradeToUpdate.getMinSalary() == 0 || salaryGradeToUpdate.getMaxSalary() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the salaryGrade, please check that all fields are correct");
        }
        return salaryGradeToUpdate;
    }

    @DeleteMapping("/{id}")
    public SalaryGrade delete(@PathVariable int id) throws SQLException{
        this.getOne(id);
        SalaryGrade salaryGradeToDelete = this.salaryGrades.delete(id);
        if(salaryGradeToDelete== null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Computer says no");
        }
        return salaryGradeToDelete;
    }
}
