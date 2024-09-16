package com.booleanuk.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salaries") public class SalaryGradeController {
    private SalaryGradeRepository sgs;

    public SalaryGradeController() throws SQLException{
        sgs = new SalaryGradeRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryGrade create(@RequestBody SalaryGrade sg) throws SQLException{
        SalaryGrade theSg = sgs.add(sg);

        if (theSg == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create.");
        }
        return theSg;
    }

    @GetMapping
    public List<SalaryGrade> getAll() throws SQLException{
        return sgs.getAll();
    }

    @GetMapping("/{id}")
    public SalaryGrade getOne(@PathVariable String grade) throws SQLException{
        SalaryGrade sg = sgs.getOne(grade);

        if (sg == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return sg;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryGrade update(@PathVariable String grade, @RequestBody SalaryGrade sg) throws SQLException {
        SalaryGrade toBeUpdated = sgs.getOne(grade);

        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if (gradeExists(sg)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to update.");
        }

        return sgs.update(grade, sg);
    }

    @DeleteMapping("/{id}")
    public SalaryGrade delete(@PathVariable String grade) throws SQLException {
        SalaryGrade toBeDeleted = sgs.getOne(grade);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return sgs.delete(grade);
    }

    private boolean gradeExists(SalaryGrade someSg) throws SQLException{
        boolean gradeExists = false;
        for (SalaryGrade sg : sgs.getAll()){
            if (sg.getMaxSalary() == someSg.getMaxSalary() && sg.getMinSalary() == someSg.getMinSalary()) {
                gradeExists = true;
                break;
            }
        }
        return gradeExists;
    }
}
