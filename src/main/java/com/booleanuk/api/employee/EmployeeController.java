package com.booleanuk.api.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;


    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee add(@RequestBody Employee employee) throws SQLException{
        Employee addEmployee = employeeRepository.add(employee);
        return addEmployee;
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return employeeRepository.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable(name = "id") int id) throws SQLException {
        return employeeRepository.getById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable(name = "id") int id, @RequestBody Employee employee) throws SQLException {
        Employee toBeUpdated = employeeRepository.getById(id);
        if(toBeUpdated == null) return null;
        return employeeRepository.update(id, employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee delete(@PathVariable(name = "id") int id) throws SQLException {
        return employeeRepository.delete(id);
    }
}
