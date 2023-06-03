package com.booleanuk.api.departments;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private DepartmentRepository repo;

    public DepartmentController() throws SQLException {
        this.repo = new DepartmentRepository();
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Department> getAll() throws SQLException {
        return this.repo.getAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department getDepartment(@PathVariable (name = "id") int id) throws SQLException {
        Department temp = this.repo.getDepartment(id);
        if(temp == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Department not found, FIX MESSAGE LATER");
        }
        return temp;
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department updateDepartment(@PathVariable (name = "id") int id, @RequestBody Department department) throws SQLException{
        Department temp = this.repo.getDepartment(id);
        if(temp == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Department with given id not found, so could not be updated");
        }
        return this.repo.updateDepartment(id,department);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department createDepartment(@RequestBody Department department) throws SQLException {
        Department temp = this.repo.addDepartment(department);
        if(temp == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Department body at fault");
        }
        return temp;
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department deleteDepartment(@PathVariable (name = "id") int id) throws SQLException {
        Department temp = this.repo.deleteDepartment(id);
        if(temp == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Failed to delete Department, check that the id is correct");
        }
        return temp;
    }
}
