package com.booleanuk.extension.department;

import com.booleanuk.extension.department.Department;
import com.booleanuk.extension.department.DepartmentRepository;
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
        if (department == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department with that id where found.");
        }
        return department;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department post(@RequestBody Department department) throws SQLException {
        Department addedDepartment = this.departmentRepository.add(department);
        if (addedDepartment != null)
            return addedDepartment;
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create department, please check all required fields are correct.");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable (name = "id") int id, @RequestBody Department department) throws SQLException {
        Department exists = departmentRepository.getOne(id);
        if (exists == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department with that id, where found.");
        Department updated = departmentRepository.update(id, department);
        if (updated == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create department, please check all required fields are correct.");
        return updated;
    }

    @DeleteMapping("/{id}")
    public Department delete(@PathVariable (name = "id") int id) throws SQLException {
        Department delete = this.departmentRepository.delete(id);
        if (delete == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department with that id where found.");
        return delete;
    }
}
