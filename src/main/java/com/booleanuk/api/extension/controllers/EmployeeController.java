package com.booleanuk.api.extension.controllers;

import com.booleanuk.api.core.models.Employee;
import com.booleanuk.api.extension.models.ExtensionEmployee;
import com.booleanuk.api.extension.repositories.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("extension_controller")
@RequestMapping("extension/employees")
public class EmployeeController {
    private final EmployeeRepository repo;

    public EmployeeController() {
        this.repo = new EmployeeRepository();
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployees() {
        return repo.getAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployee(@PathVariable(name="id") int id) {
        return repo.get(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateEmployee(@PathVariable(name="id") int id, @RequestBody ExtensionEmployee employee) {
        return repo.update(id, employee);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody ExtensionEmployee employee) {
        return repo.add(employee);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee deleteEmployee(@PathVariable(name="id") int id) {
        return repo.delete(id);
    }
}
