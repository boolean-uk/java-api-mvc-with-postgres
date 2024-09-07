package com.booleanuk.api.departments;

import com.booleanuk.api.exceptions.InvalidRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {
    private DepartmentRepository repository;

    public DepartmentController() throws SQLException {
        this.repository = new DepartmentRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department) throws SQLException {
        Department departmentObject = null;
        try {
            departmentObject = this.repository.create(department);
        } catch (InvalidRequest e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return departmentObject;
    }

    @GetMapping
    public List<Department> getAll() throws SQLException {
        return this.repository.getAll();
    }

    @GetMapping("/{id}")
    public Department getSpecific(@PathVariable long id) throws SQLException {
        Department department = this.repository.getSpecific(id);
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return department;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable (name = "id") long id, @RequestBody Department department) throws SQLException {
        Department toBeUpdated = this.repository.getSpecific(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.repository.update(id, department);
    }

    @DeleteMapping("/{id}")
    public Department delete(@PathVariable (name = "id") long id) throws SQLException {
        Department toBeDeleted = this.repository.getSpecific(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.repository.delete(id);
    }

}
