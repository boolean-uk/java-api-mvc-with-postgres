package com.booleanuk.api.department;

import com.booleanuk.api.department.Department;
import com.booleanuk.api.department.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {

    private DepartmentRepository departments;
    public  DepartmentController() throws SQLException {
        this.departments = new DepartmentRepository();
    }

    @GetMapping
    public List<Department> getAll() throws SQLException{
        return this.departments.getALl();
    }


    @GetMapping("/{id}")
    public Department getOne(@PathVariable int id) throws SQLException{
        Department department = this.departments.get(id);

        if (department == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department with that ID found");
        }
        return department;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Department create(@RequestBody Department department) throws SQLException{
        Department departmentToAdd = this.departments.add(department);
        if(departmentToAdd == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not add department");
        }
        return departmentToAdd;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{id}")
    public Department update(@PathVariable int id, @RequestBody Department department) throws SQLException{
        this.getOne(id);
        Department departmentToUpdate = this.departments.update(id, department);
        if(departmentToUpdate.getName() == null || departmentToUpdate.getLocation() == null ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the department, please check that all fields are correct");
        }
        return departmentToUpdate;
    }

    @DeleteMapping("/{id}")
    public Department delete(@PathVariable int id) throws SQLException{
        this.getOne(id);
        Department departmentToDelete = this.departments.delete(id);
        if(departmentToDelete== null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Computer says no");
        }
        return departmentToDelete;
    }

}
