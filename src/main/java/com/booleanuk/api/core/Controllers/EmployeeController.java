package com.booleanuk.api.core.Controllers;

import com.booleanuk.api.core.Models.Employee;
import com.booleanuk.api.core.Repositories.EmployeeRepository;
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
        return repo.addOne(emp);
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
    public Employee editOne(@PathVariable int id,  @RequestBody Employee newEmp) throws SQLException{
        Employee edited = repo.editOne(id, newEmp);
        if (edited == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with that ID not found");
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
