package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Department;
import com.booleanuk.api.repositories.DepartmentRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DepartmentController {
    private DepartmentRepo departmentRepo;

    public DepartmentController() throws SQLException {
        this.departmentRepo = new DepartmentRepo();
    }

    @GetMapping
    public List<Department> getAll() throws SQLException {
        return this.departmentRepo.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> get(@PathVariable long id) throws SQLException {
        return Optional.ofNullable(this.departmentRepo.get(id))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@PathVariable long id, @RequestBody Department department) throws SQLException {
        Department toBeUpdated = this.departmentRepo.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        }
        return Optional.ofNullable(this.departmentRepo.update(id, department))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the department, please check all required fields are correct."));
    }
    @PostMapping
    public ResponseEntity<Department> create(@RequestBody Department department) throws SQLException {
        return Optional.ofNullable(this.departmentRepo.add(department))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create department, please check all required fields are correct."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Department> delete(@PathVariable long id) throws SQLException {
        Department toBeDeleted = this.departmentRepo.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        }
        return ResponseEntity.ok(this.departmentRepo.delete(id));
    }
}
