package com.booleanuk.api.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    public EmployeeController() throws SQLException {
        this.employeeRepository = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.employeeRepository.getAll();
    }

    @GetMapping("{id}")
    public Employee getOne(@PathVariable int id) throws SQLException {
        Employee employee = this.employeeRepository.getOne(id);
        if (employee == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with that id");
        }
        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee post(@RequestBody Employee employee) throws SQLException {
        Employee addedEmployee = this.employeeRepository.add(employee);
        if (addedEmployee != null)
            return addedEmployee;
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable (name = "id") int id, @RequestBody Employee employee) throws SQLException {
        Employee updated = employeeRepository.update(id, employee);
        if (updated == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return updated;
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable (name = "id") int id) throws SQLException {
        Employee delete = this.employeeRepository.delete(id);
        if (delete == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return delete;
    }
}
