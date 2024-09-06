package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Department;
import com.booleanuk.api.repositories.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {
    private final DepartmentRepository departments;

    public DepartmentController() throws SQLException {
        this.departments = new DepartmentRepository();
    }

    @GetMapping
    public List<Department> getAll() throws SQLException {
        return this.departments.getAll();
    }

    @GetMapping("/{id}")
    public Department getOne(@PathVariable(name = "id") long id) throws SQLException {
        Department department = this.departments.getOne(id);
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department with that id was found");
        }
        return department;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department) throws SQLException {
        Department theDepartment = this.departments.add(department);
        System.out.println(theDepartment);
        if (theDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create department, please check all required fields are correct.");
        }
        return theDepartment;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable(name = "id") long id, @RequestBody Department department) throws SQLException {
        Department toBeUpdated = this.departments.getOne(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department with that id was found");
        }
        try {
            Department updatedDepartment = this.departments.update(id, department);
            if (updatedDepartment == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the department, please check all required fields are correct.");
            }
            return updatedDepartment;
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Department delete(@PathVariable(name = "id") long id) throws SQLException {
        Department toBeDeleted = this.departments.getOne(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department with that id was found");
        }
        return this.departments.delete(id);
    }
}
