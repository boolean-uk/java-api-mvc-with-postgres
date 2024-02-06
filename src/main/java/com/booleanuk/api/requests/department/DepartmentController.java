package com.booleanuk.api.requests.department;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class DepartmentController {
    DepartmentRepository departmentRepository;

    public DepartmentController() throws SQLException {
        departmentRepository = new DepartmentRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department createDepartment(@RequestBody Department department) throws SQLException {
        Department createdDepartment = this.departmentRepository.createDepartment(department);

        if(createdDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create department, please check all required fields are correct.");
        }

        return createdDepartment;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Department> getDepartments() throws SQLException {
        List<Department> allDepartments = this.departmentRepository.getDepartments();
        return allDepartments;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department getSpecificDepartment(@PathVariable long id) throws SQLException {
        Department specificDepartment = this.departmentRepository.getSpecificDepartment(id);

        if(specificDepartment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments with that id were found.");
        }

        return specificDepartment;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department updateDepartment(@PathVariable long id, @RequestBody Department department) throws SQLException {
        if(department.getName() == null || department.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the department, please check all required fields are correct.");
        }

        Department updatedDepartment = this.departmentRepository.updateDepartment(id, department);

        if(updatedDepartment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department with that id was found.");
        }

        return updatedDepartment;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department deleteDepartment(@PathVariable long id) throws SQLException {
        Department deletedDepartment = this.departmentRepository.deleteDepartment(id);

        if(deletedDepartment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department with that id was found.");
        }

        return deletedDepartment;
    }
}
