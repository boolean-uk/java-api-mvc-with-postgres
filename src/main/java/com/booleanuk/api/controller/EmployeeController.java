package com.booleanuk.api.controller;

import com.booleanuk.api.model.Employee;
import com.booleanuk.api.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {


    private EmployeeRepository employeeRepository;

    private EmployeeController() throws SQLException {
        employeeRepository = new EmployeeRepository();
    }


    @GetMapping
    public List<Employee> getAll() throws SQLException{

        return employeeRepository.getAll();
    }

    @GetMapping("{id}")
    public Employee getOneEmployee(@PathVariable int id)throws SQLException{

        Employee em = employeeRepository.getEmployee(id);
        if(em==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not find employee with that id");

        return em;
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) throws SQLException{

        if(employee == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to create customer");

        return employeeRepository.add(employee);

    }


    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee)throws SQLException{

        Employee em = employeeRepository.getEmployee(id);

        if(em == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not find employee with that id");

        if(employee.getJobName()==null || employee.getSalaryGrade() == 0 || employee.getDepartment() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Could not update employee");


        return this.employeeRepository.update(id,employee);

    }



    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.IM_USED)
    public Employee deleteEmployee(@PathVariable int id)throws SQLException{

        if (employeeRepository.getEmployee(id)==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee matching ID");

        return employeeRepository.delete(id);

    }


}
