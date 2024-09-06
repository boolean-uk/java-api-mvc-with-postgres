package com.booleanuk.api.departments;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentsController {
    private DepartmentsRepository departmentsRepository;

    public DepartmentsController() throws SQLException {
        this.departmentsRepository = new DepartmentsRepository();
    }


    @GetMapping
    public List<Departments> getAll() throws SQLException {
        return departmentsRepository.getAll();
    }

    @GetMapping("{id}")
    public Departments getOne(@PathVariable int id) throws SQLException {
        Departments employee = this.departmentsRepository.getOne(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nody with that id here");
        }
        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Departments add(@RequestBody Departments departments) throws SQLException {
        Departments theEmployee = this.departmentsRepository.add(departments);
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Customer");
        }
        return theEmployee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Departments update(@PathVariable (name = "id") int id, @RequestBody Departments departments) throws SQLException {
        Departments toBeUpdated = this.departmentsRepository.update(id, departments);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return toBeUpdated;
    }

    @DeleteMapping("/{id}")
    public Departments delete(@PathVariable (name = "id") int id) throws SQLException {
        Departments toBeDeleted = this.departmentsRepository.delete(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return toBeDeleted;
    }

}
