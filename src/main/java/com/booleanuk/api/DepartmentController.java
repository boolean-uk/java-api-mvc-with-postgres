package com.booleanuk.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class DepartmentController {
    private DepartmentRepository dpts;
    public DepartmentController() throws SQLException{
        dpts = new DepartmentRepository();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department) throws SQLException{
        Department theDepartment = dpts.add(department);

        if (theDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create.");
        }
        return theDepartment;
    }

    @GetMapping
    public List<Department> getAll() throws SQLException{
        return dpts.getAll();
    }

    @GetMapping("/{id}")
    public Department getOne(@PathVariable int id) throws SQLException{
        Department department = dpts.getOne(id);

        if (department == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return department;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable int id, @RequestBody Department department) throws SQLException {
        Department toBeUpdated = dpts.getOne(id);

        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if (departmentExists(department)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to update.");
        }

        return dpts.update(id, department);
    }

    @DeleteMapping("/{id}")
    public Department delete(@PathVariable int id) throws SQLException {
        Department toBeDeleted = dpts.getOne(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return dpts.delete(id);
    }

    private boolean departmentExists(Department someDpt) throws SQLException{
        boolean exists = false;
        for (Department department : dpts.getAll()){
            if (department.getLocation().equals(someDpt.getLocation()) && department.getName().equals(someDpt.getName())){
                exists = true;
            }
        }
        return exists;
    }
}