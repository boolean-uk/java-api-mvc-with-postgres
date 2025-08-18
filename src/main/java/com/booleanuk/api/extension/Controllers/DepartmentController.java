package com.booleanuk.api.extension.Controllers;

import com.booleanuk.api.extension.Models.Department;
import com.booleanuk.api.extension.Repositories.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {
    DepartmentRepository repo;

    public DepartmentController() throws SQLException{
        this.repo = new DepartmentRepository();
    }

    //Core
    @PostMapping
    public Department addDepartment(@RequestBody Department department) throws SQLException {
        Department addedDepartment = repo.addOne(department);
        if(addedDepartment == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        return addedDepartment;
    }

    @GetMapping
    public List<Department> getAll() throws SQLException{
        return repo.getAll();
    }

    @GetMapping("{id}")
    public Department getOne(@PathVariable int id) throws SQLException{
        Department department = repo.getOne(id);
        if (department == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department with that ID not found");
        }
        return department;
    }

    @PutMapping("{id}")
    public Department editOne(@PathVariable int id, @RequestBody Department newDepartment) throws SQLException{
        Department edited = repo.editOne(id, newDepartment);
        if(repo.getOne(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found");
        }
        if (edited == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update");
        }
        return edited;
    }

    @DeleteMapping("{id}")
    public Department deleteOne(@PathVariable int id) throws SQLException{
        Department deleted = repo.deleteOne(id);
        if (deleted == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department with that ID not found");
        }
        return deleted;
    }

}
