package com.booleanuk.api.salaries;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/salaries")
public class SalaryController {
    private SalaryRepository repo;

    public SalaryController() throws SQLException {
        this.repo = new SalaryRepository();
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Salary> getAll() throws SQLException {
        return this.repo.getAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary getSalary(@PathVariable (name = "id") int id) throws SQLException {
        Salary temp = this.repo.getSalary(id);
        if(temp == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No salary grades matching that id were found.");
        }
        return temp;
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salary updateSalary(@PathVariable (name = "id") int id, @RequestBody Salary salary) throws SQLException{
        Salary temp = this.repo.getSalary(id);
        if(temp == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Salary with given id not found, so could not be updated");
        }else if(temp.getId() == -1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the salary grade, please check all required fields are correct.");
        }
        return this.repo.updateSalary(id,salary);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salary createSalary(@RequestBody Salary salary) throws SQLException {
        Salary temp = this.repo.addSalary(salary);
        if(temp == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not create a new salary grade, please check all required fields are correct.");
        }
        return temp;
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Salary deleteSalary(@PathVariable (name = "id") int id) throws SQLException {
        Salary temp = this.repo.deleteSalary(id);
        if(temp == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No salary grades matching that id were found.");
        }
        return temp;
    }
}