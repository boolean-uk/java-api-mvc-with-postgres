package com.booleanuk.extension.salarygrade;

import com.booleanuk.extension.salarygrade.SalaryGrade;
import com.booleanuk.extension.salarygrade.SalaryGradeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salaries")
public class SalaryGradeController {
    private SalaryGradeRepository salaryGradeRepository;

    public SalaryGradeController() throws SQLException {
        this.salaryGradeRepository = new SalaryGradeRepository();
    }

    @GetMapping
    public List<SalaryGrade> getAll() throws SQLException {
        return this.salaryGradeRepository.getAll();
    }

    @GetMapping("{id}")
    public SalaryGrade getOne(@PathVariable int id) throws SQLException {
        SalaryGrade salaryGrade = this.salaryGradeRepository.getOne(id);
        if (salaryGrade == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salaryGrade with that id was found.");
        }
        return salaryGrade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryGrade post(@RequestBody SalaryGrade salaryGrade) throws SQLException {
        SalaryGrade addedSalaryGrade = this.salaryGradeRepository.add(salaryGrade);
        if (addedSalaryGrade != null)
            return addedSalaryGrade;
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create employee, please check all required fields are correct.");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryGrade update(@PathVariable (name = "id") int id, @RequestBody SalaryGrade salaryGrade) throws SQLException {
        SalaryGrade exists = salaryGradeRepository.getOne(id);
        if (exists == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salaryGrade with that id was found.");
        SalaryGrade updated = salaryGradeRepository.update(id, salaryGrade);
        if (updated == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create employee, please check all required fields are correct.");
        return updated;
    }

    @DeleteMapping("/{id}")
    public SalaryGrade delete(@PathVariable (name = "id") int id) throws SQLException {
        SalaryGrade delete = this.salaryGradeRepository.delete(id);
        if (delete == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salaryGrade with that id was found.");
        return delete;
    }
}
