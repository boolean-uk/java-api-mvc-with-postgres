package com.booleanuk.api;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository repo;

    public EmployeeController() throws SQLException {

        this.repo = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.repo.getAll();

    }

    @GetMapping("{id}")
    public Employee get(@PathVariable int id) throws SQLException{
        Employee customer = this.repo.get(id);
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NO Employee with that id foundd!");
        }
        return customer;
    }




    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee add(@RequestBody Employee customer) throws SQLException{
        Employee toBeAdded = this.repo.add(customer);
        if (toBeAdded == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Employee");
        }
        return  toBeAdded;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable int id, @RequestBody Employee employee) throws SQLException{
        Employee toBeUpdated = this.repo.get(id);
        if (toBeUpdated == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This is not found made by me");
        }
        return this.repo.update(id,employee);
    }


    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable int id) throws SQLException{
        Employee toBeDeleted = this.repo.delete(id);
        if (toBeDeleted == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.repo.delete(id);
    }


}

