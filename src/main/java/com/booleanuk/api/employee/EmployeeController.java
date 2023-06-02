package com.booleanuk.api.employee;

import com.booleanuk.api.department.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;


    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        Employee newEmployee;
        try {
            newEmployee = employeeRepository.add(employee);
            return newEmployee;
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create employee, please check all required fields are correct");
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = employeeRepository.getAll();
        if(employees.size() > 0) return employees;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees matching the criteria were found");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getById(@PathVariable(name = "id") int id) throws SQLException {
        Employee employee = employeeRepository.getById(id);

        if(employee == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that id were found");
        return employee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable(name = "id") int id, @RequestBody Employee employee) {
        Employee updateEmployee;
        try {
            updateEmployee = employeeRepository.update(id, employee);
        }catch (SQLException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the employee, please check all required fields are correct");
        }

        if(updateEmployee == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that id were found");

        return updateEmployee;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee delete(@PathVariable(name = "id") int id) {
        Employee deletedEmployee;
        try{
            deletedEmployee = employeeRepository.delete(id);
        }
        catch (SQLException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(deletedEmployee == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that id were found");
        return deletedEmployee;
    }
}
