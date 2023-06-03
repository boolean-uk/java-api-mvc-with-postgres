package com.booleanuk.api.extension.controllers;

import com.booleanuk.api.extension.models.Department;
import com.booleanuk.api.extension.repositories.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentRepository repo;

    public DepartmentController() {
        this.repo = new DepartmentRepository();
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Department> getDepartments() {
        return repo.getAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department getDepartment(@PathVariable(name="id") int id) {
        return repo.get(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department updateDepartment(@PathVariable(name="id") int id, @RequestBody Department department) {
        return repo.update(id, department);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department createDepartment(@RequestBody Department department) {
        return repo.add(department);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department deleteDepartment(@PathVariable(name="id") int id) {
        return repo.delete(id);
    }
}
