package com.booleanuk.api.extension.Controllers;

import com.booleanuk.api.extension.Models.Employee;
import com.booleanuk.api.extension.Repositories.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    EmployeeRepository repo;

    public EmployeeController() throws SQLException{
        this.repo = new EmployeeRepository();
    }

    //Core
    @PostMapping
    public Employee addEmployee(@RequestBody Employee emp) throws SQLException {
        Employee addedEmp = repo.addOne(emp);
        if(addedEmp == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        return addedEmp;
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException{
        return repo.getAll();
    }

    @GetMapping("{id}")
    public Employee getOne(@PathVariable int id) throws SQLException{
        Employee emp = repo.getOne(id);
        if (emp == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with that ID not found");
        }
        return emp;
    }

    @PutMapping("{id}")
    public Employee editOne(@PathVariable int id, @RequestBody Employee newEmp) throws SQLException{
        Employee edited = repo.editOne(id, newEmp);
        if(repo.getOne(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        if (edited == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update");
        }
        return edited;
    }

    @DeleteMapping("{id}")
    public Employee deleteOne(@PathVariable int id) throws SQLException{
        Employee deleted = repo.deleteOne(id);
        if (deleted == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with that ID not found");
        }
        return deleted;
    }

}
