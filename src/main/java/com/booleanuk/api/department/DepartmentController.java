package com.booleanuk.api.department;

import com.booleanuk.api.salary.Salary;
import com.booleanuk.api.salary.SalaryRepository;
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

    @GetMapping("/{name}")
    public Department getOne(@PathVariable(name = "name") String name) throws SQLException {
        Department department = this.departments.get(name);
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return department;
    }

    @PutMapping("/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable (name = "name") String name, @RequestBody Department department) throws SQLException {
        Department toBeUpdated = this.departments.get(name);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.departments.update(name ,department);
    }

    @DeleteMapping("/{name}")
    public Department delete(@PathVariable (name = "name") String name) throws SQLException {
        Department toBeDeleted = this.departments.get(name);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.departments.delete(name);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department) throws SQLException {
        Department theDepartment = this.departments.add(department);
        if (theDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Department");
        }
        return theDepartment;
    }
}
