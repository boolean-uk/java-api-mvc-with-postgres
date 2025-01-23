package com.booleanuk.api.department;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private DepartmentRepository departmentRepo;

    public DepartmentController() throws SQLException {
        this.departmentRepo = new DepartmentRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create (@RequestBody Department department) throws SQLException {
        Department createdDepartment = departmentRepo.create(department);
        if (createdDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create department, place chech all required fields are correct");
        }
        return createdDepartment;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Department> getAll() throws SQLException {
        return departmentRepo.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department getOne(long id) throws SQLException {
        Department oneDepartment = departmentRepo.getOne(id);
        if ( oneDepartment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No department with that id were found");
        }
        return oneDepartment;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable long id, @RequestBody Department department) throws SQLException {
        Department updatedDepartment = departmentRepo.update(id,department);
        if ( updatedDepartment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No department with that id were found");
        }
        return updatedDepartment;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department delete(@PathVariable long id) throws SQLException {
        Department deletedDepartment = departmentRepo.delete(id);
        if ( deletedDepartment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No department with that id were found");
        }
        return deletedDepartment;
    }
}
