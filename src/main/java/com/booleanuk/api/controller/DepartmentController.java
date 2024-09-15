package com.booleanuk.api.controller;

import com.booleanuk.api.model.Department;
import com.booleanuk.api.model.Salary;
import com.booleanuk.api.repository.DepartmentRepository;
import com.booleanuk.api.repository.SalaryRepository;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department) throws SQLException {
        Department returnDepartment = this.departmentRepository.add(department);
        if (returnDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create the new department, please check all required fields are correct.");
        }
        return returnDepartment;
    }
    @GetMapping("/{id}")
    public Department getOne(@PathVariable (name = "id") int id) throws SQLException {
        Department department = this.departmentRepository.get(id);
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        }
        return department;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable (name = "id") int id, @RequestBody Department department) throws SQLException {
        Department toBeUpdated = this.departmentRepository.get(id);


        if(!department.valuesAreAllowed(department)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not update the department, please check all required fields are correct.");

        }
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        }
        return this.departmentRepository.update(id, department);
    }

    @DeleteMapping("/{id}")
    public Department delete(@PathVariable (name = "id") int id) throws SQLException {
        Department toBeDeleted = this.departmentRepository.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        }
        return this.departmentRepository.delete(id);
    }

}
