package com.booleanuk.api.department;

import com.booleanuk.api.employee.Employee;
import com.booleanuk.api.employee.EmployeeRepository;
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
    public List<Department> getAll() throws SQLException{
        return this.departmentRepository.getAll();
    }

    @GetMapping("/{id}")
    public Department getOne(@PathVariable(name = "id") long id) throws SQLException {
        Department department = this.departmentRepository.get(id);
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return department;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department) throws SQLException {
        Department theDepartment = this.departmentRepository.add(department);
        if (theDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Department");
        }
        return theDepartment;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable (name = "id") long id, @RequestBody Department department) throws SQLException {
        Department toBeUpdated = this.departmentRepository.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.departmentRepository.update(id, department);
    }

    @DeleteMapping("/{id}")
    public Department delete(@PathVariable (name = "id") long id) throws SQLException {
        Department toBeDeleted = this.departmentRepository.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.departmentRepository.delete(id);
    }
}
