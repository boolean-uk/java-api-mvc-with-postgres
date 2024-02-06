package com.booleanuk.api.requests.salary;


import com.booleanuk.api.requests.salary.SalaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class SalaryController {
    SalaryRepository salaryRepository;

    public SalaryController() throws SQLException {
        salaryRepository = new SalaryRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary createSalary(@RequestBody Salary salary) throws SQLException {
        Salary createdSalary = this.salaryRepository.createSalary(salary);

        if(createdSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create salary, please check all required fields are correct.");
        }

        return createdSalary;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Salary> getSalaries() throws SQLException {
        List<Salary> allSalaries = this.salaryRepository.getSalaries();
        return allSalaries;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary getSpecificSalary(@PathVariable long id) throws SQLException {
        Salary specificSalary = this.salaryRepository.getSpecificSalary(id);

        if(specificSalary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salaries with that id were found.");
        }

        return specificSalary;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary updateSalary(@PathVariable long id, @RequestBody Salary salary) throws SQLException {
        if(salary.getGrade() == null || salary.getMinSalary() < 0 || salary.getMaxSalary() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the salary, please check all required fields are correct.");
        }

        Salary updatedSalary = this.salaryRepository.updateSalary(id, salary);

        if(updatedSalary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary with that id was found.");
        }

        return updatedSalary;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary deleteSalary(@PathVariable long id) throws SQLException {
        Salary deletedSalary = this.salaryRepository.deleteSalary(id);

        if(deletedSalary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary with that id was found.");
        }

        return deletedSalary;
    }
}
