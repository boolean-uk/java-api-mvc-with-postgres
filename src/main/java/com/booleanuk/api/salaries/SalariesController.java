package com.booleanuk.api.salaries;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
@RestController
@RequestMapping("salaries")

public class SalariesController {

    private SalariesRepository salariesRepository;

    public SalariesController() throws SQLException {
        this.salariesRepository = new SalariesRepository();
    }


    @GetMapping
    public List<Salaries> getAll() throws SQLException {
        return salariesRepository.getAll();
    }

    @GetMapping("{id}")
    public Salaries getOne(@PathVariable int id) throws SQLException {
        Salaries employee = this.salariesRepository.getOne(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nody with that id here");
        }
        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salaries add(@RequestBody Salaries salaries) throws SQLException {
        Salaries theEmployee = this.salariesRepository.add(salaries);
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Customer");
        }
        return theEmployee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Salaries update(@PathVariable (name = "id") int id, @RequestBody Salaries salaries) throws SQLException {
        Salaries toBeUpdated = this.salariesRepository.update(id, salaries);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return toBeUpdated;
    }

    @DeleteMapping("/{id}")
    public Salaries delete(@PathVariable (name = "id") int id) throws SQLException {
        Salaries toBeDeleted = this.salariesRepository.delete(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return toBeDeleted;
    }

}
