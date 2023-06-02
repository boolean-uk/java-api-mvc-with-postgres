package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Salary;
import com.booleanuk.api.repositories.SalaryRepository;
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

    // --------------------------------------------- //
    // -------------------- API -------------------- //
    // --------------------------------------------- //

    //region // POST //
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary create(@RequestBody Salary salary) {
        Salary newSalary;

        try { newSalary = this.repository.add(salary); }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not create salary, please check all required fields are correct."
            );
        }

        return newSalary;
    }
    //endregion

    //region // GET //
    @GetMapping
    public List<Salary> getAll() {
        List<Salary> salaries;

        try { salaries = this.repository.getAll(); }
        catch (Exception ex) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST); }

        if(salaries.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No salaries matching the criteria were found"
            );
        }
        return salaries;
    }

    @GetMapping("/{id}")
    public Salary get(@PathVariable int id) {
        Salary salary;

        try { salary = this.repository.get(id); }
        catch (Exception ex) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST); }

        if(salary == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No salaries with that id were found"
            );
        }

        return salary;
    }
    //endregion

    //region // PUT //
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary put(@PathVariable int id, @RequestBody Salary salary) {
        Salary updatedSalary;

        try { updatedSalary = this.repository.update(id, salary); }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not update the salary, please check all required fields are correct."
            );
        }

        if(updatedSalary == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No salaries with that id were found"
            );
        }

        return updatedSalary;
    }
    //endregion

    //region // DELETE //
    @DeleteMapping("/{id}")
    public Salary delete(@PathVariable int id) {
        Salary deletedSalary;

        try { deletedSalary = this.repository.delete(id); }
        catch (Exception ex) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST); }

        if(deletedSalary == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No salary with that id were found"
            );
        }

        return deletedSalary;
    }
    //endregion
}
