package com.booleanuk.api.controller;

import com.booleanuk.api.model.Employee;
import com.booleanuk.api.model.SalaryGrade;
import com.booleanuk.api.repository.SalaryGradeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salaries")
public class SalaryGradeController {




    private SalaryGradeRepository salaryGradeRepository;

    public SalaryGradeController() throws SQLException {

        this.salaryGradeRepository = new SalaryGradeRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryGrade createEmployee(@RequestBody SalaryGrade salaryGrade) throws SQLException{

        if(salaryGrade.getGrade() == null || salaryGrade.getMinSalary() == 0 || salaryGrade.getMaxSalary() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to create salary grade");

        return salaryGradeRepository.add(salaryGrade);

    }

    @GetMapping
    public List<SalaryGrade> getAall() throws SQLException {

        return salaryGradeRepository.getAll();
    }

    @GetMapping("{id}")
    public SalaryGrade getOneSalaryGrade(@PathVariable int id) throws SQLException {

        SalaryGrade sg = salaryGradeRepository.getSalaryGrade(id);
        if(sg == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Grade does not exist");

        return sg;

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SalaryGrade updateEmployee(@PathVariable int id, @RequestBody SalaryGrade salaryGrade)throws SQLException{

        SalaryGrade sg = salaryGradeRepository.getSalaryGrade(id);

        if(sg == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not find Salary Grade with that id");

        if(salaryGrade.getGrade()==null || salaryGrade.getMinSalary() == 0 || salaryGrade.getMaxSalary() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Could not update Salary Grade");


        return this.salaryGradeRepository.update(id,salaryGrade);

    }


    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.IM_USED)
    public SalaryGrade deleteSalarygrade(@PathVariable int id)throws SQLException{

        if (salaryGradeRepository.getSalaryGrade(id)==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee matching ID");

        return salaryGradeRepository.delete(id);

    }



}
