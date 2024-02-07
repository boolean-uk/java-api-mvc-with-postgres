package com.booleanuk.api.Controllers;

import com.booleanuk.api.Models.Department;
import com.booleanuk.api.Models.Employee;
import com.booleanuk.api.Repositories.DepartmentRepository;
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
    public List<Department> getAllDepartments() throws SQLException {
        return departments.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable long id) throws SQLException {
        return departments.getDepartmentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department createdepartment(@RequestBody Department department) {
        return departments.createDepartment(department);
    }

    @PutMapping("/{id}")
    public Department updatedepartment(@PathVariable long id, @RequestBody Department department) {
        try {
            Department updatedDepartment = departments.updateDepartment(id, department);
            if (updatedDepartment != null) {
                return updatedDepartment;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update department with ID " + id);
        }
    }

    @DeleteMapping("/{id}")
    public Department deleteDepartment(@PathVariable long id) throws SQLException {
        Department toBeDeleted = this.departments.getDepartmentById(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.departments.deleteDepartment(id);
    }
}
