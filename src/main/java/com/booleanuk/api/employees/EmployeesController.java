package com.booleanuk.api.employees;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("employees")
public class EmployeesController {
    private EmployeesRepository employees;

    public EmployeesController() throws SQLException {
        this.employees = new EmployeesRepository();

    }

    @GetMapping
    public List<Employees> getAll() throws SQLException {
        return this.employees.getAll();
    }

    @GetMapping("/{id}")
    public Employees getOne(@PathVariable(name = "id") long id) throws SQLException {
        Employees employees = this.employees.get(id);
        if (employees == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return employees;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employees create(@RequestBody Employees employees) throws SQLException {
        Employees theemployee = this.employees.add(employees);
        if (theemployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Customer");
        }
        return theemployee;
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employees update(@PathVariable (name = "id") long id, @RequestBody Employees employees) throws SQLException {
        Employees toBeUpdated = this.employees.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employees.update(id, employees);
    }

    @DeleteMapping("/{id}")
    public Employees delete(@PathVariable (name = "id") long id) throws SQLException {
        Employees toBeDeleted = this.employees.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employees.delete(id);
    }


}
