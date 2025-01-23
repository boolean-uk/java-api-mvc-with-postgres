package com.booleanuk.api.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository employeeRepo;

    public EmployeeController() throws SQLException {
        this.employeeRepo = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getEmployees() throws SQLException {
        return this.employeeRepo.getAll();
    }

    @GetMapping("/{id}")
    public Employee getOneEmployee(@PathVariable(name = "id") int id) throws SQLException {
        return this.employeeRepo.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createOneEmployee(@RequestBody Employee employee) throws SQLException {
        return this.employeeRepo.add(employee);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateOneEmployee(@PathVariable(name = "id") int id, @RequestBody Employee employee) throws SQLException {
        return this.employeeRepo.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public Employee deleteOneEmployee(@PathVariable(name = "id") int id) throws SQLException {
        return this.employeeRepo.delete(id);
    }
}
