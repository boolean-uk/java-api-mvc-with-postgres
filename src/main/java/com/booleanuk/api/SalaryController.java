package com.booleanuk.api;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salaries")
public class SalaryController {
    private SalaryRepository repo;

    public SalaryController() throws SQLException {

        this.repo = new SalaryRepository();
    }

    @GetMapping
    public List<Salary> getAll() throws SQLException {
        return this.repo.getAll();

    }

    @GetMapping("{id}")
    public Salary get(@PathVariable int id) throws SQLException{
        Salary customer = this.repo.get(id);
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NO Salary with that id foundd!");
        }
        return customer;
    }




    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary add(@RequestBody Salary customer) throws SQLException{
        Salary toBeAdded = this.repo.add(customer);
        if (toBeAdded == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Salary");
        }
        return  toBeAdded;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary update(@PathVariable int id, @RequestBody Salary employee) throws SQLException{
        Salary toBeUpdated = this.repo.get(id);
        if (toBeUpdated == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This is not found made by me");
        }
        return this.repo.update(id,employee);
    }


    @DeleteMapping("/{id}")
    public Salary delete(@PathVariable int id) throws SQLException{
        Salary toBeDeleted = this.repo.delete(id);
        if (toBeDeleted == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.repo.delete(id);
    }


}

