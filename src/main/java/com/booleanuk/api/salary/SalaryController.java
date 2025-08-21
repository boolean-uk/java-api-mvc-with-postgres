package com.booleanuk.api.salary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salaries")
public class SalaryController {
    private SalaryRepository salaryRepository;

    public SalaryController() throws SQLException {
        this.salaryRepository =  new SalaryRepository();
    }

    @GetMapping
    public List<Salary> getAll() throws SQLException {
        return this.salaryRepository.getAll();
    }

    @PostMapping
    public Salary addSalary(@RequestBody Salary salary) throws SQLException {
        Salary newSalary = this.salaryRepository.add(salary);
        if (newSalary == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create new Salary");
        } else {
            throw new ResponseStatusException(HttpStatus.CREATED, "Successfully created a new Salary");
        }
    }

    @GetMapping("{id}")
    public Salary getOne(@PathVariable int id) throws SQLException {
        Salary salary = this.salaryRepository.getOne(id);
        if (salary == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Salary with the provided id was found");
        } else {
            return salary;
        }
    }

    @PutMapping("{id}")
    public Salary updateSalary(@PathVariable int id, @RequestBody Salary salary) throws SQLException {
        Salary updatedSalary = this.salaryRepository.update(id, salary);
        if (updatedSalary == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Salary with the provided id was found");
        } else {
            throw new ResponseStatusException(HttpStatus.CREATED, "Successfully updated the specified Salary");
        }
    }

    @DeleteMapping("{id}")
    public Salary deleteSalary(@PathVariable int id) throws SQLException {
        Salary salary = this.salaryRepository.delete(id);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Salary with the provided id was found");
        } else {
            throw new ResponseStatusException(HttpStatus.OK, "Successfully deleted the specified Salary");
        }
    }
}
