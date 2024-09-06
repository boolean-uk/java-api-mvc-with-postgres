package com.booleanuk.api.departments;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentRepository repository;

    public DepartmentController() throws SQLException {
        this.repository = new DepartmentRepository();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Department> getAll() throws SQLException {
        return this.repository.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department get(@PathVariable (name = "id") int id) throws SQLException {
        return this.repository.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department) throws SQLException {
        return this.repository.add(department);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable (name = "id") int id, @RequestBody Department department) {
        try {
            return this.repository.update(id, department);
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Could not update the department, please check all required fields are correct.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department delete(@PathVariable (name = "id") int id) throws SQLException {
        return this.repository.delete(id);
    }
}
