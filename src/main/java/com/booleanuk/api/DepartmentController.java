package com.booleanuk.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private DepartmentRepository departments;

    public DepartmentController() throws SQLException {
        this.departments = new DepartmentRepository();

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Department> getAllDepartments() throws SQLException {
        return this.departments.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department getOneDepartment(@PathVariable(name = "id") long id) throws SQLException {
        Department department = this.departments.getOne(id);
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return department;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department createDepartment(@RequestBody Department department) throws SQLException {
        Department theDepartment = this.departments.add(department);
        if (theDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified department");
        }
        return theDepartment;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department updateDepartment(@PathVariable (name = "id") long id, @RequestBody Department department) throws SQLException {
        Department toBeUpdated = this.departments.getOne(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.departments.update(id, department);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department deleteDepartment(@PathVariable (name = "id") long id) throws SQLException {
        Department toBeDeleted = this.departments.getOne(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.departments.delete(id);
    }
}
