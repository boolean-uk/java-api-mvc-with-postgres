package com.booleanuk.api.salary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salaries")
public class SalaryController {

    private final SalaryRepository salaryRepository;

    @Autowired
    public SalaryController(SalaryRepository salaryRepository){
        this.salaryRepository = salaryRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Salary> getAll() throws SQLException {
        List<Salary> salaries = salaryRepository.getAll();
        if(salaries.size() > 0) return salaries;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching the criteria were found");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary getById(@PathVariable(name = "id") int id) throws SQLException {
        Salary salary = salaryRepository.getById(id);

        if(salary == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching that id were found");
        return salary;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable(name = "id") int id, @RequestBody Salary salary){
        Salary updateSalary;
        try {
            updateSalary = salaryRepository.update(id, salary);
        }catch (SQLException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the salary grade, please check all required fields are correct");
        }

        if(updateSalary == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching that id were found");

        return updateSalary;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary delete(@PathVariable(name = "id") int id) {
        Salary deletedSalary;
        try{
            deletedSalary = salaryRepository.delete(id);
        }
        catch (SQLException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(deletedSalary == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching that id were found");
        return deletedSalary;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary create(@RequestBody Salary salary){

        Salary newSalary;
        try {
            newSalary = salaryRepository.add(salary);
            return newSalary;
        }
        catch (SQLException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create a new salary grade, please check all required fields are correct");
        }
    }
}
