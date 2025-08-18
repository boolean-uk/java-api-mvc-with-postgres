package com.booleanuk.api.Salary;

import com.booleanuk.api.Employee.Employee;
import com.booleanuk.api.Employee.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salaries")
public class SalaryController {
    private SalaryRepository repository;

    public SalaryController() throws SQLException {
        this.repository = new SalaryRepository();
    }

    @GetMapping
    public List<Salary> getAll() throws SQLException {
        return this.repository.getAll();
    }

    @GetMapping("{id}")
    public Salary getOne(@PathVariable int id) throws SQLException {
        return this.repository.get(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Salary addEmployee(@RequestBody Salary salary) {
        if (salary.getMinSalary() > salary.getMaxSalary()) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not create a new salary grade, please check all required fields are correct");
        }
        try {
            return this.repository.add(salary);
        } catch (Exception e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not create a new salary grade, please check all required fields are correct");
        }

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable int id, @RequestBody Salary salary) throws SQLException {
        return this.repository.update(id, salary);
    }

    @DeleteMapping("{id}")
    public Salary delete(@PathVariable int id) throws SQLException {
        return this.repository.delete(id);
    }

}
