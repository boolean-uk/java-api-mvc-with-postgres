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
    private DepartmentRepository departments;

    public DepartmentController() throws SQLException {
        this.departments = new DepartmentRepository();
    }

    @GetMapping
    public List<Department> getAll() throws SQLException{
        return this.departments.getAll();
    }

    @GetMapping("/{id}")
    public Department getOne(@PathVariable int id) throws SQLException{
        Department department = this.departments.get(id);
        if (department == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
        }
        return department;
    }

    @PutMapping("/{id}")
    public Department update(@PathVariable int id, @RequestBody Department department) throws SQLException{
        return this.departments.update(id,department);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department addDepartment(@RequestBody Department department) throws SQLException{
        return this.departments.add(department);
    }

    @DeleteMapping("/{id}")
    public Department deleteDepartment(@PathVariable int id) throws SQLException{
        return this.departments.delete(id);
    }
}
