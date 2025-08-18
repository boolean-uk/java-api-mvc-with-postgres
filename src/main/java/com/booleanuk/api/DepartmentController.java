package com.booleanuk.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {
    private DepartmentRepository departmentRepository;

    public DepartmentController() throws SQLException {
        this.departmentRepository = new DepartmentRepository();
    }

    @GetMapping
    public List<Department> getAll() throws SQLException {
        return this.departmentRepository.getAll();
    }

    @GetMapping("{id}")
    public Department getOne(@PathVariable int id) throws SQLException {
        Department department = this.departmentRepository.getOne(id);

        // extensions
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department with that id found");
        }
        return department;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department) throws SQLException {
        Department theDepartment = this.departmentRepository.add(department);

        // extensions
        if (theDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified department");
        }
        return department;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable (name = "id") int id, @RequestBody Department department) throws SQLException {
        Department toBeUpdated = this.departmentRepository.getOne(id);

        // extension
        if ((department.getName() == null || department.getLocation() == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not sufficient data");
        }
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given department does not exits");
        }
        return this.departmentRepository.update(id, department);
    }

    // possible to refactor
    @DeleteMapping("/{id}")
    public Department delete(@PathVariable (name = "id") int id) throws SQLException {
        Department toBeDeleted = this.departmentRepository.getOne(id);

        // extension
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given department does not existss");
        }
        return this.departmentRepository.delete(id);
    }
}
