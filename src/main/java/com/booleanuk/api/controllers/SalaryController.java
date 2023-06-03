package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Salary;
import com.booleanuk.api.repositories.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
@RestController
@RequestMapping("salaries")
public class SalaryController {
    @Autowired
    private SalaryRepository salaryRepository;
    @GetMapping()
    public List<Salary> getAll() throws SQLException {
        List<Salary> salaries=salaryRepository.getAll();
        if (salaries.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No salary grades matching the criteria were found!");
        }
        return salaries;
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary getById(@PathVariable(name="id") int id) throws SQLException {
        Salary salary = salaryRepository.get(id);
        if (salary==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No salary grades matching that id found!");
        }
        return salary;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary create(@RequestBody Salary salary) throws SQLException {
        if ((salary.getGrade().isEmpty() || salary.getGrade() != null)||
                (salary.getMinSalary() < 1 ) || (salary.getMaxSalary() < 1 )){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not create a new salary grade, please check all required fields are correct!");
        }

        Salary theSalary = this.salaryRepository.add(salary);
        if (theSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Salary");
        }
        return theSalary;
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable(name="id") int id,@RequestBody Salary salary) throws SQLException {
        if ((salary.getGrade().isEmpty() || salary.getGrade() != null)||
                (salary.getMinSalary() < 1 ) || (salary.getMaxSalary() < 1 )){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not update the salary grade, please check all required fields are correct!");
        }

        Salary toBeUpdated = this.salaryRepository.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }

        if (!toBeUpdated.getGrade().equals(salary.getGrade())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grade matching that id were found!");
        }

        return this.salaryRepository.update(id, salary);
    }
    @DeleteMapping("/{id}")
    public Salary delete(@PathVariable (name = "id") long id) throws SQLException {
        Salary toBeDeleted = this.salaryRepository.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching that id were found!");
        }
        return this.salaryRepository.delete(id);
    }
}
