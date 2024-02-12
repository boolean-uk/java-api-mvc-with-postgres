package com.booleanuk.api.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    public EmployeeController() throws SQLException{
        this.employeeRepository = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException{
        return this.employeeRepository.getAll();
    }


    @GetMapping("/{id}")
    public Employee getOne(@PathVariable(name = "id") int id) throws SQLException{
        Employee employee = this.employeeRepository.getOne(id);

        if (employee == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException{
        Employee createEmployee = this.employeeRepository.add(employee);
        if (createEmployee == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request cant create employee");
        }
        return createEmployee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable(name = "id") int id, @RequestBody Employee employee) throws SQLException{
        Employee updateEmployee = this.employeeRepository.getOne(id);
        if (updateEmployee == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request cant update employee");
        }
        return this.employeeRepository.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable(name = "id") int id) throws SQLException{
        Employee deleteEmployee = this.employeeRepository.getOne(id);
        if(deleteEmployee == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request cant delete employee");
        }
        return this.employeeRepository.delete(id);
    }
}
