package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Department;
import com.booleanuk.api.repositories.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private DepartmentRepository repository;

    public DepartmentController() throws SQLException {
        this.repository = new DepartmentRepository();
    }

    // --------------------------------------------- //
    // -------------------- API -------------------- //
    // --------------------------------------------- //

    //region // POST //
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department) {
        Department newDepartment;

        try { newDepartment = this.repository.add(department); }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not create department, please check all required fields are correct."
            );
        }
        return newDepartment;
    }
    //endregion

    //region // GET //
    @GetMapping
    public List<Department> getAll() {
        List<Department> departments;

        try { departments = this.repository.getAll(); }
        catch (Exception ex) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST); }

        if(departments.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No departments matching the criteria were found"
            );
        }
        return departments;
    }

    @GetMapping("/{id}")
    public Department get(@PathVariable int id) {
        Department department;

        try { department = this.repository.get(id); }
        catch (Exception ex) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST); }

        if(department == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No departments with that id were found"
            );
        }

        return department;
    }
    //endregion

    //region // PUT //
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department put(@PathVariable int id, @RequestBody Department department) {
        Department updatedDepartment;

        try { updatedDepartment = this.repository.update(id, department); }
        catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not update the department, please check all required fields are correct."
            );
        }

        if(updatedDepartment == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No departments with that id were found"
            );
        }

        return updatedDepartment;
    }
    //endregion

    //region // DELETE //
    @DeleteMapping("/{id}")
    public Department delete(@PathVariable int id) {
        Department deletedDepartment;

        try { deletedDepartment = this.repository.delete(id); }
        catch (Exception ex) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST); }

        if(deletedDepartment == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No department with that id were found"
            );
        }

        return deletedDepartment;
    }
    //endregion
}
