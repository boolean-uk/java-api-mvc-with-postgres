package com.booleanuk.api.extension.departments;

import com.booleanuk.api.extension.salaries.Salary;
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

    @GetMapping("{id}")
    public Department getOne(@PathVariable int id) throws SQLException {
        Department department = this.departments.getOne(id);
        if(department != null)  {
            return department;
        }   else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department add(@RequestBody Department department) throws SQLException {
        if(department.getName() == null || department.getLocation() == null)    {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not create the new department, please check all required fields are correct"
            );
        }

        Department addedDepartment = departments.add(department);
        if (addedDepartment != null)
            return addedDepartment;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Tried to add department, but can't find it in the database");
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable int id, @RequestBody Department department) throws SQLException {
        if(department.getName() == null || department.getLocation() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not update the department, please check all required fields are correct"
            );
        }

        Department updatedDepartment = departments.update(id, department);
        if (updatedDepartment != null)
            return updatedDepartment;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Tried to update department, but can't find it in the database anymore");
    }

    @DeleteMapping("{id}")
    public Department delete(@PathVariable int id) throws SQLException {
        Department deletedDepartment = this.departments.delete(id);
        if(deletedDepartment == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        }
        return deletedDepartment;
    }
}
