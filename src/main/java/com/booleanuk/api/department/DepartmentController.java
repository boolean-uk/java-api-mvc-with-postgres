package com.booleanuk.api.department;

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
        this.departmentRepository =  new DepartmentRepository();
    }

    @GetMapping
    public List<Department> getAll() throws SQLException {
        return this.departmentRepository.getAll();
    }

    @PostMapping
    public Department addDepartment(@RequestBody Department department) throws SQLException {
        Department newDepartment = this.departmentRepository.add(department);
        if (newDepartment == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create new Department");
        } else {
            throw new ResponseStatusException(HttpStatus.CREATED, "Successfully created a new Department");
        }
    }

    @GetMapping("{id}")
    public Department getOne(@PathVariable int id) throws SQLException {
        Department department = this.departmentRepository.getOne(id);
        if (department == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Department with the provided id was found");
        } else {
            return department;
        }
    }

    @PutMapping("{id}")
    public Department updateDepartment(@PathVariable int id, @RequestBody Department department) throws SQLException {
        Department updatedDepartment = this.departmentRepository.update(id, department);
        if (updatedDepartment == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Department with the provided id was found");
        } else {
            throw new ResponseStatusException(HttpStatus.CREATED, "Successfully updated the specified Department");
        }
    }

    @DeleteMapping("{id}")
    public Department deleteDepartment(@PathVariable int id) throws SQLException {
        Department department = this.departmentRepository.delete(id);
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Department with the provided id was found");
        } else {
            throw new ResponseStatusException(HttpStatus.OK, "Successfully deleted the specified Department");
        }
    }
}
