package com.booleanuk.api.extension.Controllers;

import com.booleanuk.api.extension.Models.Salary;
import com.booleanuk.api.extension.Repositories.SalariesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("salaries")
public class SalariesController {
    SalariesRepository repo;

    public SalariesController() throws SQLException{
        this.repo = new SalariesRepository();
    }

    //Core
    @PostMapping
    public Salary addSalary(@RequestBody Salary salary) throws SQLException {
        Salary addedSalary = repo.addOne(salary);
        if(addedSalary == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        return addedSalary;
    }

    @GetMapping
    public List<Salary> getAll() throws SQLException{
        return repo.getAll();
    }

    @GetMapping("{id}")
    public Salary getOne(@PathVariable int id) throws SQLException{
        Salary salary = repo.getOne(id);
        if (salary == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Salary with that ID not found");
        }
        return salary;
    }

    @PutMapping("{id}")
    public Salary editOne(@PathVariable int id, @RequestBody Salary newSalary) throws SQLException{
        Salary edited = repo.editOne(id, newSalary);
        if(repo.getOne(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Salary not found");
        }
        if (edited == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update");
        }
        return edited;
    }

    @DeleteMapping("{id}")
    public Salary deleteOne(@PathVariable int id) throws SQLException{
        Salary deleted = repo.deleteOne(id);
        if (deleted == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Salary with that ID not found");
        }
        return deleted;
    }

}
