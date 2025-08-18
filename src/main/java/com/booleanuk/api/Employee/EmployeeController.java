package com.booleanuk.api.Employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository repository;

    public EmployeeController() throws SQLException {
        this.repository = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.repository.getAll();
    }

    @GetMapping("{id}")
    public Employee getOne(@PathVariable int id) throws SQLException {
        return this.repository.get(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) throws SQLException {
        try {
            return this.repository.add(employee);
        } catch (Exception e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not create a new salary grade, please check all required fields are correct");
        }
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable int id, @RequestBody Employee employee) throws SQLException {
        return this.repository.update(id, employee);
    }

    @DeleteMapping("{id}")
    public Employee delete(@PathVariable int id) throws SQLException {
        return this.repository.delete(id);
    }

}
