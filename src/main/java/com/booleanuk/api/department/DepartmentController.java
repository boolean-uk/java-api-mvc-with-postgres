package com.booleanuk.api.department;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department createOne(@RequestBody Department department) throws SQLException {
        Department e = this.departments.add(department);
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Something went wrong; did not create department.");
        }
        return e;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Department> getAll() throws SQLException {
        List<Department> es = departments.getAll();
        if (es.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments found.");
        }
        return es;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department getOne(@PathVariable(name="id") int id) throws SQLException {
        Department e = departments.getOne(id);
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found.");
        }
        return e;
    }

    @PutMapping("/{id}")
    public Department updateOne(@PathVariable(name="id") int id, @RequestBody Department department) throws SQLException {
        Department e = departments.updateOne(id, department);
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found.");
        }
        return e;
    }

    @DeleteMapping("/{id}")
    public Department deleteOne(@PathVariable(name="id") int id) throws SQLException {
        Department e = departments.deleteOne(id);
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found.");
        }
        return e;
    }
}
