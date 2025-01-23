package com.booleanuk.api;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository repo;

    public EmployeeController() throws SQLException {
        repo = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return repo.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) throws SQLException {
        return repo.add(employee);
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable int id) throws SQLException {
        return repo.getOne(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable int id, @RequestBody Employee employee) throws SQLException {
        return repo.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable int id) throws SQLException {
        return repo.remove(id);
    }
}
