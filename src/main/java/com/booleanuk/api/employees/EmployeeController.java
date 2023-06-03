package com.booleanuk.api.employees;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeRepository repo;

    public EmployeeController() throws SQLException {
        this.repo = new EmployeeRepository();
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAll() throws SQLException {
        return this.repo.getAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployee(@PathVariable (name ="id") int id) throws SQLException {
        Employee temp = this.repo.getEmployee(id);
        if(temp == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Employee with given id not found");
        }
        return temp;
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateEmployee(@PathVariable (name ="id") int id, @RequestBody Employee employee) throws SQLException {
        Employee temp = this.repo.getEmployee(id);
        if(temp == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Employee with given id not found, so could not be updated");
        }
        return this.repo.updateEmployee(id,employee);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) throws SQLException {
        Employee temp = this.repo.addEmployee(employee);
        if(temp == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Could not create employee");
        }
        return temp;
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee deleteEmployee(@PathVariable (name = "id") int id) throws SQLException {
        Employee temp = this.repo.deleteEmployee(id);
        if(temp == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Failed to delete Employee, check that the id is correct");
        }
        return temp;
    }
}
