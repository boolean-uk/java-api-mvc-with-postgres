package com.booleanuk.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.booleanuk.api.model.Department;
import com.booleanuk.api.repository.DepartmentRepository;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("departments")
public class DepartmentController {
    
    private DepartmentRepository departments;

    public DepartmentController(){
        this.departments = new DepartmentRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department add(@RequestBody Department toAdd) throws SQLException{
        Department added = this.departments.add(toAdd);
        if (added != null) return added;
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create departmet.");
    }
    
    @GetMapping()
    public List<Department> getAll() throws SQLException {
        return this.departments.getAll();
    }
    
    @GetMapping("{id}")
    public Department getOne(@PathVariable long id) throws SQLException{
        Department d = this.departments.getOne(id);
        if (d != null) return d;
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department not found.");
    }

    @DeleteMapping("{id}")
    public Department delete(@PathVariable long id) throws SQLException{
        Department toDelete = this.departments.getOne(id);
        if(toDelete != null) return toDelete;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found.");
    }

    @PutMapping("{id}")
    public Department update(@PathVariable long id, @RequestBody Department toUpdate) throws SQLException {
        Department actualToUpdate = this.departments.getOne(id);
        if (actualToUpdate != null) return this.departments.update(id, toUpdate);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.");
    }


}
