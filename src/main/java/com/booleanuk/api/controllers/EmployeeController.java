package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Department;
import com.booleanuk.api.models.Employee;
import com.booleanuk.api.models.Salary;
import com.booleanuk.api.repositories.DepartmentRepository;
import com.booleanuk.api.repositories.EmployeeRepository;
import com.booleanuk.api.repositories.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SalaryRepository salaryRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @GetMapping()
    public List<Employee> getAll() throws SQLException {
        List<Employee> employees =employeeRepository.getAll();
        if (employees.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No employees matching the criteria were found");
        }
        return employees;
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee getById(@PathVariable(name="id") int id) throws SQLException {
        Employee employee = employeeRepository.get(id);
        if (employee==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No employees with that id found");
        }
        return employee;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException {

        if ((employee.getName().isEmpty()||employee.getName()==null)||
                (employee.getJobName().isEmpty()||employee.getJobName()==null)||
                (employee.getSalaryGrade().isEmpty()||employee.getSalaryGrade()==null)||
                (employee.getDepartment().isEmpty()||employee.getDepartment()==null)
        ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create employee, please check all required fields are correct.");
        }
        Salary salary =salaryRepository.getByGrade(employee.getSalaryGrade());
        if (salary==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grade found.");
        }
        Department department =departmentRepository.getByName(employee.getDepartment());
        if (department==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department found.");
        }

        Employee theEmployee = this.employeeRepository.add(employee);
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Customer");
        }
        return theEmployee;
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable(name="id") int id,@RequestBody Employee employee) throws SQLException {

        if ((employee.getName().isEmpty()||employee.getName()==null)||
                (employee.getJobName().isEmpty()||employee.getJobName()==null)||
                (employee.getSalaryGrade().isEmpty()||employee.getSalaryGrade()==null)||
                (employee.getDepartment().isEmpty()||employee.getDepartment()==null)
        ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create employee, please check all required fields are correct.");
        }

        Salary salary =salaryRepository.getByGrade(employee.getSalaryGrade());
        if (salary==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grade found.");
        }
        Department department =departmentRepository.getByName(employee.getDepartment());
        if (department==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department found.");
        }

        Employee toBeUpdated = this.employeeRepository.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employeeRepository.update(id, employee);
    }
    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable (name = "id") long id) throws SQLException {
        Employee toBeDeleted = this.employeeRepository.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employeeRepository.delete(id);
    }
}
