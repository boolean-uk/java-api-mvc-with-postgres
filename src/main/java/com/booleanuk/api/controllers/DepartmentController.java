package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Department;
import com.booleanuk.api.models.Employee;
import com.booleanuk.api.repositories.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {
    private DepartmentRepository departments;

    public DepartmentController() throws SQLException {
        this.departments = new DepartmentRepository();
    }

    @GetMapping
    public List<Department> getAll() throws SQLException {
        return this.departments.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department) throws SQLException {
        if (department.getName() == null || department.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        Department theDepartment =  this.departments.add(department);
        if (theDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        return theDepartment;
    }

    @GetMapping("/{id}")
    public Department get(@PathVariable(name = "id") int id) throws SQLException {
        Department theDepartment =  this.departments.get(id);
        if (theDepartment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return theDepartment;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable(name="id") int id, @RequestBody Department department) throws SQLException {
        if (department.getName() == null || department.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        Department theDepartment = this.departments.update(id, department);
        if (theDepartment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return theDepartment;
    }
}
