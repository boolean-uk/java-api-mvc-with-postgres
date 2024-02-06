package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Department;
import com.booleanuk.api.repositories.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/departments")
public class DepartmentController {
    DepartmentRepository repository;

    @Autowired
    public DepartmentController(DepartmentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        log.info("Getting all departments");
        return repository.getAllDepartments();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department addDepartment(@RequestBody Department department) {
        try {
            log.info("Adding new " + department);
            if (Stream.of(department.getDepartmentName(), department.getDepartmentLocation())
                    .anyMatch(field -> field == null || field.isBlank())) {
                throw new IllegalArgumentException("Required fields are missing/empty.");
            }
            int id = repository.addDepartment(department);
            return repository.getDepartment(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create department. " + e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public Department getDepartment(@PathVariable int id) {
        try {
            log.info("Getting Department(id=" + id + ")");
            return repository.getDepartment(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments with id= " + id + " found");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department updateDepartment(@PathVariable int id, @RequestBody Department department) {
        try {
            log.info("Updating Department(id=" + id + ") with values from " + department);
            if (Stream.of(department.getDepartmentName(), department.getDepartmentLocation())
                    .anyMatch(field -> field == null || field.isBlank())) {
                throw new IllegalArgumentException("Required fields are missing/empty.");
            }
            repository.updateDepartment(id, department);
            return repository.getDepartment(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments with id= " + id + " found");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create department. " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Department deleteDepartment(@PathVariable int id) {
        try {
            log.info("Deleting Department(id=" + id + ")");
            Department deletedDepartment = repository.getDepartment(id);
            repository.deleteDepartment(id);
            return deletedDepartment;
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments with id= " + id + " found");
        }
    }
}
