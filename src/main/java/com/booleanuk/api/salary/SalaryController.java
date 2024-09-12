package com.booleanuk.api.salary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("salaries")
public class SalaryController {

    private SalaryRepository salaryRepository;

    public SalaryController() throws SQLException{
        this.salaryRepository = new SalaryRepository();
    }

    @GetMapping
    public List<Salary> getAll() throws SQLException{
        return this.salaryRepository.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salary> get(@PathVariable int id) throws SQLException{
        return Optional.ofNullable(this.salaryRepository.get(id)).map(ResponseEntity::ok).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Didn't find id"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Salary> update(@PathVariable int id, @RequestBody Salary salary) throws SQLException{
        Salary update = this.salaryRepository.get(id);
        if(update == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No id found");
        }
        return Optional.ofNullable(this.salaryRepository.updated(id, salary)).map(ResponseEntity::ok).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update salary"));
    }

    @PostMapping
    public ResponseEntity<Salary> create(@RequestBody Salary salary) throws SQLException{
        return Optional.ofNullable(this.salaryRepository.add(salary)).map(ResponseEntity::ok).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Salary> delete(@PathVariable int id) throws SQLException{
        Salary delete = this.salaryRepository.get(id);
        if(delete == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grade id");
        }
        return ResponseEntity.ok(this.salaryRepository.delete(id));
    }

}
