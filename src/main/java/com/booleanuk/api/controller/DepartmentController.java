package com.booleanuk.api.controller;

import com.booleanuk.api.model.Department;
import com.booleanuk.api.model.DepartmentRepository;
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

        if (department == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        }
        return department;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department) throws SQLException {
        Department theDepartment = this.departmentRepository.add(department);

        if (theDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create the new department, please check all required fields are correct.");
        }
        return department;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable (name = "id") int id, @RequestBody Department department) throws SQLException {
        Department toBeUpdated = this.departmentRepository.getOne(id);

        if ((department.getName() == null || department.getLocation() == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the new department, please check all required fields are correct");
        }
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        }
        return this.departmentRepository.update(id, department);
    }

    @DeleteMapping("/{id}")
    public Department delete(@PathVariable (name = "id") int id) throws SQLException {
        Department toBeDeleted = this.departmentRepository.getOne(id);

        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        }
        return this.departmentRepository.delete(id);
    }
}