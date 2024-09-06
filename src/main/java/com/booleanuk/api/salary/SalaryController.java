package com.booleanuk.api.salary;

import com.booleanuk.api.department.Department;
import com.booleanuk.api.department.DepartmentRepository;
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
        this.salaryRepository = new SalaryRepository();
    }

    @GetMapping
    public List<Salary> getAll() throws SQLException{
        return this.salaryRepository.getAll();
    }

    @GetMapping("/{grade}")
    public Salary getOne(@PathVariable(name = "grade") String grade) throws SQLException {
        Salary salary = this.salaryRepository.get(grade);
        if (salary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return salary;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary create(@RequestBody Salary salary) throws SQLException {
        Salary theSalary = this.salaryRepository.add(salary);
        if (theSalary == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Department");
        }
        return theSalary;
    }

    @PutMapping("/{grade}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable (name = "grade") String grade, @RequestBody Salary salary) throws SQLException {
        Salary toBeUpdated = this.salaryRepository.get(grade);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.salaryRepository.update(grade, salary);
    }

    @DeleteMapping("/{grade}")
    public Salary delete(@PathVariable (name = "grade") String grade) throws SQLException {
        Salary toBeDeleted = this.salaryRepository.get(grade);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.salaryRepository.delete(grade);
    }
}
