package com.booleanuk.api.controller;

import com.booleanuk.api.model.Department;
import com.booleanuk.api.repository.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmenController {





    private DepartmentRepository departmentRepository;

    public DepartmenController() throws SQLException {

        this.departmentRepository = new DepartmentRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department createDepartment(@RequestBody Department department) throws SQLException{

        if(department.getName() == null || department.getLocation() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to create salary grade");

        return departmentRepository.add(department);

    }

    @GetMapping
    public List<Department> getAall() throws SQLException {

        return departmentRepository.getAll();
    }

    @GetMapping("{id}")
    public Department getOneDepartment(@PathVariable int id) throws SQLException {

        Department sg = departmentRepository.getDepartment(id);
        if(sg == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department does not exist");

        return sg;

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department updateEmployee(@PathVariable int id, @RequestBody Department salaryGrade)throws SQLException{

        Department sg = departmentRepository.getDepartment(id);

        if(sg == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not find Salary Grade with that id");

        if(salaryGrade.getName()==null || salaryGrade.getLocation() == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Could not update Salary Grade");


        return this.departmentRepository.update(id,salaryGrade);

    }


    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.IM_USED)
    public Department deleteSalarygrade(@PathVariable int id)throws SQLException{

        if (departmentRepository.getDepartment(id)==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee matching ID");

        return departmentRepository.delete(id);

    }



}
