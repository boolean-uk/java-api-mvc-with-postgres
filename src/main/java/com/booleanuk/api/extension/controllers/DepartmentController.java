package com.booleanuk.api.extension.controllers;

import com.booleanuk.api.core.models.Employee;
import com.booleanuk.api.extension.models.Department;
import com.booleanuk.api.extension.repositories.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("extension/departments")
public class DepartmentController {
    private final DepartmentRepository repo;

    public DepartmentController() {
        this.repo = new DepartmentRepository();
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Department> getDepartments() {
        List<Department> departments = repo.getAll();

        if (departments.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching the criteria were found");

        return departments;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department getDepartment(@PathVariable(name="id") int id) {
        Department requestedDepartment = repo.get(id);

        if (requestedDepartment == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");

        return requestedDepartment;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department updateDepartment(@PathVariable(name="id") int id, @RequestBody Department department) {
        Department updatedDepartment = null;

        try {
            updatedDepartment = repo.update(id, department);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't update the department, please check all required fields are correct");
        }

        if (updatedDepartment == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");

        return updatedDepartment;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department createDepartment(@RequestBody Department department) {
        Department createdDepartment = null;

        try {
            createdDepartment =  repo.add(department);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't update the department, please check all required fields are correct");
        }

        return createdDepartment;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department deleteDepartment(@PathVariable(name="id") int id) {
        Department deletedDepartment = repo.delete(id);

        if (deletedDepartment == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");

        return deletedDepartment;
    }
}
