package com.booleanuk.api.departments;

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

    @GetMapping("{id}")
    public Department getOne(@PathVariable int id) throws SQLException {
        Department employee = this.departments.get(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Department does not exist!");
        }
        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department employee) throws SQLException {
        Department createdDepartment = this.departments.add(employee);
        if (createdDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Unable to create the specified Department");
        }
        return createdDepartment;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable (name = "id") int id, @RequestBody Department department) throws SQLException {
        Department updatedDepartment = this.departments.get(id);
        if (updatedDepartment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.departments.update(id, department);
    }

    @DeleteMapping("{id}")
    public Department delete(@PathVariable (name = "id") int id) throws SQLException {
        Department deletedDepartment = this.departments.get(id);
        if (deletedDepartment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.departments.delete(id);
    }
}
