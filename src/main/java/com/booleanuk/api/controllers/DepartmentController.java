package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Department;
import com.booleanuk.api.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
@RestController
@RequestMapping("departments")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;
    @GetMapping()
    public List<Department> getAll() throws SQLException {
        return departmentRepository.getAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department getById(@PathVariable(name="id") int id) throws SQLException {
        return departmentRepository.get(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department) throws SQLException {
        Department theDepartment = this.departmentRepository.add(department);
        if (theDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Department");
        }
        return theDepartment;
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable(name="id") int id,@RequestBody Department department) throws SQLException {
        Department toBeUpdated = this.departmentRepository.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.departmentRepository.update(id, department);
    }
    @DeleteMapping("/{id}")
    public Department delete(@PathVariable (name = "id") long id) throws SQLException {
        Department toBeDeleted = this.departmentRepository.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.departmentRepository.delete(id);
    }
}
