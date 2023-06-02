package com.booleanuk.api.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Department> getAll() throws SQLException {
        List<Department> departments = departmentRepository.getAll();
        if(departments.size() > 0) return departments;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments were found");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department getById(@PathVariable(name = "id") int id) throws SQLException {
        Department department = departmentRepository.getById(id);

        if(department == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        return department;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable(name = "id") int id, @RequestBody Department department){
        Department updateDepartment;
        try {
            updateDepartment = departmentRepository.update(id, department);
        }catch (SQLException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the department, please check all required fields are correct");
        }

        if(updateDepartment == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");

        return updateDepartment;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department delete(@PathVariable(name = "id") int id) {
        Department deletedDepartment;
        try{
            deletedDepartment = departmentRepository.delete(id);
        }
        catch (SQLException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(deletedDepartment == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        return deletedDepartment;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department){

        Department newDepartment;
        try {
            newDepartment = departmentRepository.add(department);
            return newDepartment;
        }
        catch (SQLException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create the new department, please check all required fields are correct");
        }
    }
}
