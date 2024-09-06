package com.booleanuk.api.employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeRepository repository;

    public EmployeeController() throws SQLException {
        this.repository = new EmployeeRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDTO create(@RequestBody Employee employee) throws SQLException {
        try {
            return this.repository.add(employee);
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Could not create employee, please check all required fields are correct.");
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDTO> getAll() throws SQLException {
        return this.repository.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO get(@PathVariable (name = "id") long id) throws SQLException {
        return this.repository.get(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDTO update(@PathVariable (name = "id") long id, @RequestBody Employee employee) {
        try {
            return this.repository.update(id, employee);
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Could not update the employee, please check all required fields are correct.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO delete(@PathVariable (name = "id") long id) throws SQLException {
        return this.repository.delete(id);
    }
}
