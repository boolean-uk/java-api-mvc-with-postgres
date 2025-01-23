package com.booleanuk.api.department;

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

    @GetMapping("/{id}")
    public Department getOne(@PathVariable(name = "id") int id) throws SQLException {
        Department department = this.departments.get(id);
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return department;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department) throws SQLException {
        Department theDepartment = this.departments.add(department);
        if (theDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Employee");
        }
        return theDepartment;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable (name = "id") int id, @RequestBody Department department) throws SQLException {
        Department toBeUpdated = this.departments.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.departments.update(id, department);
    }

    @DeleteMapping("/{id}")
    public Department delete(@PathVariable (name = "id") int id) throws SQLException {
        Department toBeDeleted = this.departments.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.departments.delete(id);
    }
}
