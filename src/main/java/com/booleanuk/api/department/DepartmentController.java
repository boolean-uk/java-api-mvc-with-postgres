package com.booleanuk.api.department;

import com.booleanuk.api.employee.Employee;
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

    @GetMapping("/{id}")
    public Department getOne(@PathVariable int id) throws SQLException {
        Department department = this.departmentRepository.getOne(id);

        if (department == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department with this id");
        }
        return department;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department) throws SQLException {
        Department newOne = this.departmentRepository.add(department);
        if (newOne == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create new department with this information");
        } else if (departmentRepository.getAll().contains(department)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The department exists already");
        } else {
            throw new ResponseStatusException(HttpStatus.CREATED, "New department created successfully");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable(name = "id") int id, @RequestBody Department department) throws SQLException {
        Department updatedOne = this.departmentRepository.update(id, department);
        if (updatedOne == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department with this id");
        } else {
            throw new ResponseStatusException(HttpStatus.CREATED, "Department updated successfully");
        }
    }

    @DeleteMapping("/{id}")
    public Department delete(@PathVariable int id) throws SQLException {
        Department deletedOne = this.departmentRepository.delete(id);

        if (deletedOne == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department with this id");
        }
        else {
            throw new ResponseStatusException(HttpStatus.OK, "Department deleted successfully");
        }
    }
}
